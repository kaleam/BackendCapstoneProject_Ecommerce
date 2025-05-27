package com.ecommerce.usermanagementservice.services;

import com.ecommerce.usermanagementservice.dtos.UserEvent;
import com.ecommerce.usermanagementservice.exceptions.*;
import com.ecommerce.usermanagementservice.models.NotificationType;
import com.ecommerce.usermanagementservice.models.Session;
import com.ecommerce.usermanagementservice.models.State;
import com.ecommerce.usermanagementservice.models.User;
import com.ecommerce.usermanagementservice.producers.UserEventProducer;
import com.ecommerce.usermanagementservice.repos.ISessionRepository;
import com.ecommerce.usermanagementservice.repos.IUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements IUserService{
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ISessionRepository sessionRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserEventProducer userEventProducer;

    @Autowired
    private SecretKey secretKey;

    @Override
    public User signup(User user) throws UsernameAlreadyExists, EmailAlreadyExists {
        // check if username exists
        if(userRepository.findByUsername(user.getUsername()).isPresent())
            throw new UsernameAlreadyExists("Username already taken");
        // check if email exists
        if(userRepository.findByEmail(user.getEmail()).isPresent())
            throw new EmailAlreadyExists("Email already exist");
        // encrypt password
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setState(State.ACTIVE);
        // save User entity
        User savedUser = userRepository.save(user);
        // publish event to Kafka
        UserEvent userEvent = prepareUserEvent(savedUser);
        userEventProducer.publishEvent(userEvent);
        // return User entity
        return userRepository.save(savedUser);
    }

    @Override
    public Pair<User, MultiValueMap<String, String>> login(String username, String password) throws UsernameDoesNotExist, WrongPassword {
        // check if username exists
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty())
            throw new UsernameDoesNotExist("Username does not exist");
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password, user.getPassword()))
            throw new WrongPassword("Wrong password");
        return new Pair<>(user, generateToken(user));
    }

    @Override
    public boolean updateProfile(Long id, User updateUser) throws IdDoesNotExist, EmailAlreadyExists {
        // check if id exists
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty())
            throw new IdDoesNotExist("Id not found");
        User user = userOptional.get();
        if(updateUser.getEmail() != null) {
            if(userRepository.findByEmail(updateUser.getEmail()).isPresent())
                throw new EmailAlreadyExists("Email already exist");
            user.setEmail(updateUser.getEmail());
        }
        if(updateUser.getFullName() != null)
            user.setFullName(updateUser.getFullName());
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean resetPassword(Long id, String password) throws IdDoesNotExist{
        // check if id exists
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty())
            throw new IdDoesNotExist("Id not found");
        User user = userOptional.get();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean validateToken(String token, Long userId) throws SessionDoesNotExist, SessionIsNotActive {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);
        // check if session exists
        if(sessionOptional.isEmpty())
            throw new SessionDoesNotExist("Session does not exist");
        Session session = sessionOptional.get();
        // check if session is active
        if(session.getState() != State.ACTIVE)
            throw new SessionIsNotActive("Session is not active");
        // check if token is valid
        JwtParser parser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = parser.parseSignedClaims(token).getPayload();
        long expiryTime = claims.getExpiration().getTime();
        long currentTime = System.currentTimeMillis();
        if(currentTime > expiryTime) {
            System.out.println("Token Expired !!!");
            return false;
        }
        return true;
    }

    @Override
    public void logout(String token, Long userId) throws SessionDoesNotExist {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);
        // check if session exists
        if(sessionOptional.isEmpty())
            throw new SessionDoesNotExist("Session does not exist");
        Session session = sessionOptional.get();
        // set session state to INACTIVE
        session.setState(State.INACTIVE);
        sessionRepository.save(session);
    }

    private UserEvent prepareUserEvent(User user) {
        UserEvent userEvent = new UserEvent();
        userEvent.setUserId(user.getUsername());
        userEvent.setEmail(user.getEmail());
        userEvent.setPhoneNumber("1234567890");
        userEvent.setMessage("Welcome to our service, " + user.getFullName() + "!");
        userEvent.setType(NotificationType.EMAIL);
        return userEvent;
    }

    private MultiValueMap<String, String> generateToken(User user){
        Map<String,Object> claims = new HashMap<>();
        claims.put("user_id", user.getId());
        claims.put("user_email", user.getEmail());
        long timeInMillis = System.currentTimeMillis();
        claims.put("iat", timeInMillis);
        // expiry 10 minutes
        claims.put("exp", timeInMillis+600000);
        claims.put("iss", "abhijeet");

        String token = Jwts.builder().claims(claims).signWith(secretKey).compact();

        MultiValueMap<String, String> headers= new LinkedMultiValueMap<>();
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(600) // 10 minutes
                .build();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

        // Save session
        Session session = new Session();
        session.setState(State.ACTIVE);
        session.setToken(token);
        session.setUser(user);

        sessionRepository.save(session);

        return headers;
    }


}

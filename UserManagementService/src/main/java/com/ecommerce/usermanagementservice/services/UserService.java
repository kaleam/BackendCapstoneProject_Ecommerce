package com.ecommerce.usermanagementservice.services;

import com.ecommerce.usermanagementservice.exceptions.*;
import com.ecommerce.usermanagementservice.models.User;
import com.ecommerce.usermanagementservice.repos.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
        // save and return User entity
        return userRepository.save(user);
    }

    @Override
    public boolean login(String username, String password) throws UsernameDoesNotExist, WrongPassword {
        // check if username exists
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty())
            throw new UsernameDoesNotExist("Username does not exist");
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password, user.getPassword()))
            throw new WrongPassword("Wrong password");
        return true;
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
}

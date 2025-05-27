package com.ecommerce.usermanagementservice.controllers;

import com.ecommerce.usermanagementservice.dtos.*;
import com.ecommerce.usermanagementservice.models.User;
import com.ecommerce.usermanagementservice.services.IUserService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        try {
            User user = new User();
            user.setUsername(signUpRequestDto.getUsername());
            user.setPassword(signUpRequestDto.getPassword());
            user.setEmail(signUpRequestDto.getEmail());
            user.setFullName(signUpRequestDto.getFullName());

            User savedUser = userService.signup(user);

            signUpResponseDto.setId(savedUser.getId());
            signUpResponseDto.setUsername(savedUser.getUsername());
            signUpResponseDto.setEmail(savedUser.getEmail());
            signUpResponseDto.setFullName(savedUser.getFullName());
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        MultiValueMap<String, String> headers;
        try{
            Pair<User, MultiValueMap<String, String>> loginResponse =  userService.login(loginRequestDto.getUsername(),loginRequestDto.getPassword());
            loginResponseDto.setMessage("Login successful");
            User user = loginResponse.a;
            loginResponseDto.setUserId(user.getId());
            headers = loginResponse.b;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return new ResponseEntity<>(loginResponseDto, headers, HttpStatus.OK);
    }

    @PatchMapping({"{id}"})
    public ResponseEntity<UpdateProfileResponseDto> updateProfile(@PathVariable Long id, @RequestBody UpdateProfileRequestDto updateProfileRequestDto) {
        UpdateProfileResponseDto updateProfileResponseDto = new UpdateProfileResponseDto();
        try{
            User user = new User();
            user.setEmail(updateProfileRequestDto.getEmail());
            user.setFullName(updateProfileRequestDto.getFullName());
            userService.updateProfile(id,user);
            updateProfileResponseDto.setMessage("Update successful");
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return new ResponseEntity<>(updateProfileResponseDto, HttpStatus.OK);
    }

    @PostMapping("reset/{id}")
    public ResponseEntity<ResetPasswordResponseDto> resetPassword(@PathVariable Long id, @RequestBody ResetPasswordRequestDto resetPasswordRequestDto) {
        ResetPasswordResponseDto resetPasswordResponseDto = new ResetPasswordResponseDto();
        try{
            userService.resetPassword(id,resetPasswordRequestDto.getPassword());
            resetPasswordResponseDto.setMessage("Reset successful");
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return new ResponseEntity<>(resetPasswordResponseDto, HttpStatus.OK);
    }

    @PostMapping("validateToken")
    public boolean validateToken(@RequestBody ValidateTokenDto validateTokenDto) {
        try {
            return userService.validateToken(validateTokenDto.getToken(), validateTokenDto.getUserId());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("logout")
    public ResponseEntity<LogoutResponseDto> logout(@RequestBody LogoutRequestDto logoutRequestDto) {
        LogoutResponseDto logoutResponseDto = new LogoutResponseDto();
        try {
            userService.logout(logoutRequestDto.getToken(), logoutRequestDto.getUserId());
            logoutResponseDto.setMessage("Logout successful");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return new ResponseEntity<>(logoutResponseDto, HttpStatus.OK);
    }

}

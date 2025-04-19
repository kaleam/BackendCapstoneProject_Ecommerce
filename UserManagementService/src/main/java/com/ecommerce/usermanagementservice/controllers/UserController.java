package com.ecommerce.usermanagementservice.controllers;

import com.ecommerce.usermanagementservice.dtos.*;
import com.ecommerce.usermanagementservice.models.User;
import com.ecommerce.usermanagementservice.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
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
        return signUpResponseDto;
    }

    @PostMapping("login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        try{
            userService.login(loginRequestDto.getUsername(),loginRequestDto.getPassword());
            loginResponseDto.setMessage("Login successful");
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return loginResponseDto;
    }

    @PatchMapping({"{id}"})
    public UpdateProfileResponseDto updateProfile(@PathVariable Long id, @RequestBody UpdateProfileRequestDto updateProfileRequestDto) {
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
        return updateProfileResponseDto;
    }

    @PostMapping("reset/{id}")
    public ResetPasswordResponseDto resetPassword(@PathVariable Long id, @RequestBody ResetPasswordRequestDto resetPasswordRequestDto) {
        ResetPasswordResponseDto resetPasswordResponseDto = new ResetPasswordResponseDto();
        try{
            userService.resetPassword(id,resetPasswordRequestDto.getPassword());
            resetPasswordResponseDto.setMessage("Reset successful");
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return resetPasswordResponseDto;
    }

}

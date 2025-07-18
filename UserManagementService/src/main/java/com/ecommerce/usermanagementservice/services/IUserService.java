package com.ecommerce.usermanagementservice.services;

import com.ecommerce.usermanagementservice.exceptions.*;
import com.ecommerce.usermanagementservice.models.User;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.util.MultiValueMap;

public interface IUserService {
    User signup(User user) throws UsernameAlreadyExists, EmailAlreadyExists;

    Pair<User, MultiValueMap<String, String>> login(String username, String password) throws UsernameDoesNotExist, WrongPassword;

    boolean updateProfile(Long id, User user, String token) throws IdDoesNotExist, EmailAlreadyExists, InvalidRequest;

    boolean resetPassword(Long id, String password, String token) throws IdDoesNotExist, InvalidRequest;

    boolean validateToken(String token) throws SessionDoesNotExist, SessionIsNotActive;

    void logout(String token) throws SessionDoesNotExist;
}

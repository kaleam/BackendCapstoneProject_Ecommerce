package com.ecommerce.usermanagementservice.services;

import com.ecommerce.usermanagementservice.exceptions.*;
import com.ecommerce.usermanagementservice.models.User;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.util.MultiValueMap;

public interface IUserService {
    User signup(User user) throws UsernameAlreadyExists, EmailAlreadyExists;
    Pair<User, MultiValueMap<String, String>> login(String username, String password) throws UsernameDoesNotExist, WrongPassword;
    boolean updateProfile(Long id, User user) throws IdDoesNotExist, EmailAlreadyExists;
    boolean resetPassword(Long id, String password) throws IdDoesNotExist;
    boolean validateToken(String token, Long userId) throws SessionDoesNotExist, SessionIsNotActive;
    void logout(String token, Long userId) throws SessionDoesNotExist;
}

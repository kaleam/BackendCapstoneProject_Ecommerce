package com.ecommerce.usermanagementservice.services;

import com.ecommerce.usermanagementservice.exceptions.*;
import com.ecommerce.usermanagementservice.models.User;

public interface IUserService {
    public User signup(User user) throws UsernameAlreadyExists, EmailAlreadyExists;
    public boolean login(String username, String password) throws UsernameDoesNotExist, WrongPassword;
    public boolean updateProfile(Long id, User user) throws IdDoesNotExist, EmailAlreadyExists;
    public boolean resetPassword(Long id, String password) throws IdDoesNotExist;
}

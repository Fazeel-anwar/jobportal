package com.jobportal.service;

import com.jobportal.entity.User;

import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    Optional<User> login(String email, String password);
}

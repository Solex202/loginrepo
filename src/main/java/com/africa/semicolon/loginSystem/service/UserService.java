package com.africa.semicolon.loginSystem.service;

import com.africa.semicolon.loginSystem.data.model.User;
import com.africa.semicolon.loginSystem.dtos.request.UserRequest;
import com.africa.semicolon.loginSystem.dtos.response.UserResponse;

import java.util.Collection;
import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);

    List<User> getAllUsers();
}

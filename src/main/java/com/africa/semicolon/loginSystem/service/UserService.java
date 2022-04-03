package com.africa.semicolon.loginSystem.service;

import com.africa.semicolon.loginSystem.data.model.User;
import com.africa.semicolon.loginSystem.dtos.request.CreateUserRequest;
import com.africa.semicolon.loginSystem.dtos.response.CreateUserResponse;

import java.util.List;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest request);

    List<User> getAllUsers();
}

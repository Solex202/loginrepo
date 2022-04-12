package com.africa.semicolon.loginSystem.service;

import com.africa.semicolon.loginSystem.data.model.User;
import com.africa.semicolon.loginSystem.dtos.request.CreateUserRequest;
import com.africa.semicolon.loginSystem.dtos.request.LoginRequest;
import com.africa.semicolon.loginSystem.dtos.request.UpdateRequest;
import com.africa.semicolon.loginSystem.dtos.response.*;

import java.util.List;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest request);

    List<User> getAllUsers();

    LoginResponse login(LoginRequest login);

    UpdateResponse updateUsername(UpdateRequest updateUsernameRequest, String password);

    FindUserResponse findByUserName(String johnson202);

    DeleteResponse deleteByUsername(String username);
}

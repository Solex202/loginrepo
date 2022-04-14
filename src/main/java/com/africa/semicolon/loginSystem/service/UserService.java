package com.africa.semicolon.loginSystem.service;

import com.africa.semicolon.loginSystem.data.model.User;
import com.africa.semicolon.loginSystem.dtos.request.CreateUserRequest;
import com.africa.semicolon.loginSystem.dtos.request.LoginRequest;
import com.africa.semicolon.loginSystem.dtos.request.UpdateRequest;
import com.africa.semicolon.loginSystem.dtos.response.*;

import java.util.Collection;
import java.util.List;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest request);

    List<User> getAllUsers();

//    List<User> deleteAllUsers();

    LoginResponse login(LoginRequest login);


    FindUserResponse findByUserName(String username);

    DeleteResponse deleteByUsername(String username);

    UpdateResponse updateUsername(String password, UpdateRequest updateRequest);

//    DeleteResponse deleteAllUsers();

    UpdateResponse updateFirstName(String password, UpdateRequest updateRequest);


    UpdateResponse updatePassword(String password, UpdateRequest updateRequest);
}

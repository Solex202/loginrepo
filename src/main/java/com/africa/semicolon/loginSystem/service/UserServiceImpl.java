package com.africa.semicolon.loginSystem.service;

import com.africa.semicolon.loginSystem.data.model.User;
import com.africa.semicolon.loginSystem.data.repository.UserRepo;
import com.africa.semicolon.loginSystem.dtos.request.CreateUserRequest;
import com.africa.semicolon.loginSystem.dtos.response.CreateUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{


    @Autowired
    private UserRepo repository;

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        User newUser = new User();


        newUser.setFirstName(request.getFirstName().toLowerCase());
        newUser.setLastName(request.getLastName().toLowerCase());
        newUser.setUserName(request.getUserName().toLowerCase());
        newUser.setPassword(request.getPassword());
        repository.save(newUser);

        CreateUserResponse response = new CreateUserResponse();
        response.setFullName(newUser.getFirstName() +" " + newUser.getLastName());
        response.setMessage("user registered");

        return response;
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }
}

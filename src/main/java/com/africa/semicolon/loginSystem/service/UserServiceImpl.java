package com.africa.semicolon.loginSystem.service;

import com.africa.semicolon.loginSystem.data.model.User;
import com.africa.semicolon.loginSystem.data.repository.UserRepo;
import com.africa.semicolon.loginSystem.dtos.request.UserRequest;
import com.africa.semicolon.loginSystem.dtos.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{


    @Autowired
    private UserRepo repository;

    @Override
    public UserResponse createUser(UserRequest request) {
        User newUser = new User();


        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setUserName(request.getUserName());
        newUser.setPassword(request.getPassword());
        repository.save(newUser);

        UserResponse response = new UserResponse();
        response.setFullName(newUser.getFirstName() +" " + newUser.getLastName());
        response.setMessage("user registered");

        return response;
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }
}

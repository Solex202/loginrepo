package com.africa.semicolon.loginSystem.service;

import com.africa.semicolon.loginSystem.data.model.User;
import com.africa.semicolon.loginSystem.data.repository.UserRepo;
import com.africa.semicolon.loginSystem.dtos.request.CreateUserRequest;
import com.africa.semicolon.loginSystem.dtos.request.LoginRequest;
import com.africa.semicolon.loginSystem.dtos.response.CreateUserResponse;
import com.africa.semicolon.loginSystem.dtos.response.LoginResponse;
import com.africa.semicolon.loginSystem.exception.IncorrectPasswordException;
import com.africa.semicolon.loginSystem.exception.InvalidPasswordException;
import com.africa.semicolon.loginSystem.exception.UserAlreadyExistsException;
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
        if(userAlreadyExist(request.getUserName())) throw new UserAlreadyExistsException("user already exist");
        if(!passwordIsValid(request.getPassword())) throw new InvalidPasswordException("Invalid password");

        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setUserName(request.getUserName());
        newUser.setPassword(request.getPassword());
        repository.save(newUser);

        CreateUserResponse response = new CreateUserResponse();
        response.setFullName(newUser.getFirstName() +" " + newUser.getLastName());
        response.setMessage("user registered");

        return response;
    }

    private boolean userAlreadyExist(String userName) {
        User myUser = repository.findByUserName(userName);
        return myUser != null;
    }

    private boolean passwordIsValid(String password) {
        return password.length() >= 8;
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User myUser = repository.findByUserNameAndPassword(loginRequest.getUsername(),loginRequest.getPassword());
        LoginResponse response = new LoginResponse();
        if(myUser != null) {
          response.setMessage("loginRequest successful");
          return response;
        }

        if(passwordIsIncorrect(loginRequest.getPassword())) throw new IncorrectPasswordException("incorrect password");
        return null;
    }

    private boolean passwordIsIncorrect(String password) {

        User myUser = repository.findByPassword(password);
        if(myUser == null){
            return true;
        }
        return false;
    }
}

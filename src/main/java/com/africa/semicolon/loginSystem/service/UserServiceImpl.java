package com.africa.semicolon.loginSystem.service;

import com.africa.semicolon.loginSystem.data.model.User;
import com.africa.semicolon.loginSystem.data.repository.UserRepo;
import com.africa.semicolon.loginSystem.dtos.request.CreateUserRequest;
import com.africa.semicolon.loginSystem.dtos.request.LoginRequest;
import com.africa.semicolon.loginSystem.dtos.request.UpdateRequest;
import com.africa.semicolon.loginSystem.dtos.response.CreateUserResponse;
import com.africa.semicolon.loginSystem.dtos.response.FindUserResponse;
import com.africa.semicolon.loginSystem.dtos.response.LoginResponse;
import com.africa.semicolon.loginSystem.dtos.response.UpdateResponse;
import com.africa.semicolon.loginSystem.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo repository;

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        User newUser = new User();

        if(userAlreadyExist(request.getUserName(), request.getPassword())) throw new UserAlreadyExistsException("user already exist");
        if(!passwordIsValid(request.getPassword())) throw new InvalidPasswordException("Invalid password");
        if(usernameAlreadyExist(request.getUserName())) throw new UsernameAlreadyExistsException("username already exist, try another one");

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

    private boolean usernameAlreadyExist(String userName) {

        return repository.findByUserName(userName) != null ;
//        return myUser != null;
    }

    private boolean userAlreadyExist(String userName, String password) {
//        for (int i = 0; i < getAllUsers().size(); i++) {
//            if(getAllUsers().get(i).getUserName())
//
//        }
        return repository.findByUserName(userName) != null &&  repository.findByPassword(password) != null;
//        return myUser != null;
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
        User myUser = repository.findByUserName(loginRequest.getUsername());
        if(myUser == null){ throw new IncorrectUsernameException("Incorrect Username");}
        if(!myUser.getPassword().equals(loginRequest.getPassword())) throw new IncorrectPasswordException("Password is incorrect");
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("LoginRequest successfull");
        return loginResponse;

    }

    @Override
    public UpdateResponse updateUsername(UpdateRequest updateRequest, String password) {
        User myUser = repository.findByUserNameAndPassword(updateRequest.getUserName(), updateRequest.getPassword());
        UpdateResponse response = new UpdateResponse();
        if (myUser != null){
            updateRequest.setUserName("ginibby101");
            response.setMsg("username updated");
        }
        return response;
    }

    @Override
    public FindUserResponse findByUserName(String username) {
        if(userDoesNotExist(username)) throw new UserNotFoundException("user not found");
        User myUser = repository.findByUserName(username);

        FindUserResponse response = new FindUserResponse();
        response.setFirstName(myUser.getFirstName());
        response.setLastName(myUser.getLastName());
        response.setUserName(myUser.getUserName());

        return response;
    }

    private boolean userDoesNotExist(String username) {
        return repository.findByUserName(username) == null;
    }

//


}

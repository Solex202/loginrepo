package com.africa.semicolon.loginSystem.service;

import com.africa.semicolon.loginSystem.data.model.User;
import com.africa.semicolon.loginSystem.data.repository.UserRepo;
import com.africa.semicolon.loginSystem.dtos.request.CreateUserRequest;
import com.africa.semicolon.loginSystem.dtos.request.LoginRequest;
import com.africa.semicolon.loginSystem.dtos.request.UpdateRequest;
import com.africa.semicolon.loginSystem.dtos.response.CreateUserResponse;
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
        User myUser = repository.findByUserName(loginRequest.getUsername());
        if(myUser == null){ throw new IncorrectUsernameException("Incorrect Username");}
        if(!myUser.getPassword().equals(loginRequest.getPassword())) throw new IncorrectPasswordException("Password is incorrect");
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("LoginRequest successfull");
        return loginResponse;

//        User myUser = repository.findByUserNameAndPassword(loginRequest.getUsername(),loginRequest.getPassword());
//        LoginResponse response = new LoginResponse();
//
//        if(myUser != null) {
//          response.setMessage("loginRequest successfull");
//          return response;
//        }
//        if(usernameIsIncorrect(loginRequest.getUsername(),loginRequest.getPassword())){
//            throw new IncorrectUsernameException("incorrect username");
//        }
//        if(passwordIsIncorrect(loginRequest.getPassword(), loginRequest.getUsername())){
//            throw new IncorrectPasswordException("incorrect password or username");
//        }
//        if(userDoesNotExist(loginRequest.getPassword(), loginRequest.getUsername())) {
//            throw new UserNotFoundException("user doesn't exist exception");
//        }

//        log.info(loginRequest.getUsername(), loginRequest.getPassword());
//        User myUser = repository.findByUserNameAndPassword(loginRequest.getUsername(),loginRequest.getPassword());
//        System.out.println(myUser);
//        if (myUser != null) {
////            LoginResponse response = new LoginResponse();
//            response.setMessage("loginRequest successful");
//            return response;
//        }
//        throw new UserNotFoundException("user doesn't exist exception");

    }

    @Override
    public UpdateResponse updateUsername(UpdateRequest updateUsernameRequest, String password) {
//        User myUser = repository.findByUserNameAndPassword(updateUsernameRequest.getUsername(), updateUsernameRequest.getPassword());
//        UpdateUsernameResponse response = new UpdateUsernameResponse();
//        if (myUser != null){
//            updateUsernameRequest.setPassword("deedeji101");
////            response.
//        }
        return null;
    }

//

    private boolean userDoesNotExist(String password, String username) {
        User myUser = repository.findByUserNameAndPassword(password, username);
        if(myUser == null){
           return true;
        }
        return false;
    }

    private boolean passwordIsIncorrect(String password, String username) {

        if(repository.findByPassword(password) == null || repository.findByUserName(username) == null){
            return true;
        }
        return false;
    }
}

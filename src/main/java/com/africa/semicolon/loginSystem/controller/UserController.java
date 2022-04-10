package com.africa.semicolon.loginSystem.controller;


import com.africa.semicolon.loginSystem.data.model.User;
import com.africa.semicolon.loginSystem.dtos.ApiResponse;
import com.africa.semicolon.loginSystem.dtos.request.CreateUserRequest;
import com.africa.semicolon.loginSystem.dtos.request.LoginRequest;
import com.africa.semicolon.loginSystem.dtos.response.CreateUserResponse;
import com.africa.semicolon.loginSystem.dtos.response.LoginResponse;
import com.africa.semicolon.loginSystem.exception.IncorrectPasswordException;
import com.africa.semicolon.loginSystem.exception.InvalidPasswordException;
import com.africa.semicolon.loginSystem.exception.UserAlreadyExistsException;
import com.africa.semicolon.loginSystem.exception.UserNotFoundException;

import com.africa.semicolon.loginSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<?> response(@RequestBody CreateUserRequest request){
        try{
            return new ResponseEntity<>(userService.createUser(request), HttpStatus.OK);
        }catch(UserAlreadyExistsException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.ALREADY_REPORTED);
        }
        catch(InvalidPasswordException ex){
            return new ResponseEntity<>( new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> response(@RequestBody LoginRequest request){
        try{
            return new ResponseEntity<>(userService.login(request), HttpStatus.OK);
        }catch(IncorrectPasswordException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.UNAUTHORIZED);
        }
        catch(UserNotFoundException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/getUsers")
    public List<User> getAllUsers (){
        return userService.getAllUsers();
    }
}

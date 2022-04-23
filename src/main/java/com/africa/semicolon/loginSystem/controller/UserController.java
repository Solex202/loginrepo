package com.africa.semicolon.loginSystem.controller;


import com.africa.semicolon.loginSystem.data.model.User;
import com.africa.semicolon.loginSystem.dtos.ApiResponse;
import com.africa.semicolon.loginSystem.dtos.request.CreateUserRequest;
import com.africa.semicolon.loginSystem.dtos.request.LoginRequest;
import com.africa.semicolon.loginSystem.dtos.request.UpdateRequest;
import com.africa.semicolon.loginSystem.exception.*;

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
        }catch(UserAlreadyExistsException | UsernameAlreadyExistsException ex){
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
        }catch(IncorrectPasswordException | IncorrectUsernameException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/getUsers")
    public List<User> getAllUsers (){
        return userService.getAllUsers();
    }

    @GetMapping("findBy/{username}")
    public ResponseEntity<?> findUserName(@PathVariable String username){
        try{

        return new ResponseEntity<>(userService.findByUserName(username), HttpStatus.OK);
        }catch (UserNotFoundException ex){
            return new ResponseEntity<>(new ApiResponse(false,ex.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deleteBy/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username){
        return new ResponseEntity<>(userService.deleteByUsername(username), HttpStatus.OK);
    }

    @PatchMapping("/updateUsername")
    public ResponseEntity<?> updateByUsername(@RequestBody String password, UpdateRequest request){
        return new ResponseEntity<>(userService.updateUsername(password,request),HttpStatus.OK);
    }

    @PatchMapping("/updateLastname")
    public  ResponseEntity<?> updateByLastname(@RequestBody  String password, UpdateRequest request){
        return  new ResponseEntity<>(userService.updateLastName(password,request),HttpStatus.OK);
    }
    @PatchMapping("/updateFirstname")
    public  ResponseEntity<?> updateByFirstname(@RequestBody  String password, UpdateRequest request){
        return  new ResponseEntity<>(userService.updateFirstName(password,request),HttpStatus.OK);
    }
    @PatchMapping("/updatePassword")
    public  ResponseEntity<?> updateByPassword(@RequestBody  String password, UpdateRequest request){
        return  new ResponseEntity<>(userService.updatePassword(password,request),HttpStatus.OK);
    }
}

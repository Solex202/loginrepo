package com.africa.semicolon.loginSystem.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String msg) {
        System.out.println(msg);
    }
}

package com.africa.semicolon.loginSystem.exception;

public class UserAlreadyExistsException extends RuntimeException{


    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}

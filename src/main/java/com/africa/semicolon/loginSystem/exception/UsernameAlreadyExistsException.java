package com.africa.semicolon.loginSystem.exception;

public class UsernameAlreadyExistsException extends RuntimeException{
    public  UsernameAlreadyExistsException(String message){
        super(message);
    }
}

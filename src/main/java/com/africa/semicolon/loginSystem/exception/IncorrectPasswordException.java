package com.africa.semicolon.loginSystem.exception;

public class IncorrectPasswordException extends RuntimeException{

    public IncorrectPasswordException(String msg){
        System.out.println(msg);
    }


}

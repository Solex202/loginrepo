package com.africa.semicolon.loginSystem.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String msg) {
        System.out.println(msg);
    }
}

package com.africa.semicolon.loginSystem.dtos.request;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
}

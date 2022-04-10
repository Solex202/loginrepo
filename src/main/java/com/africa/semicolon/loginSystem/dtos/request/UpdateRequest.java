package com.africa.semicolon.loginSystem.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
@RequiredArgsConstructor
public class UpdateRequest {

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
}

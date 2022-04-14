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

    private String oldFirstName;
    private String newFirstName;
    private String lastName;
    private String oldUsername;
    private String newUsername;
    private String oldPassword;
    private String newPassword;
}

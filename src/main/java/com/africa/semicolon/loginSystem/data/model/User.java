package com.africa.semicolon.loginSystem.data.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("User")
@ComponentScan("com.africa.semicolon.loginSystem")
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

}

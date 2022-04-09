package com.africa.semicolon.loginSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.africa.semicolon.loginSystem")
public class LoginSystemMain {
    public static void main(String [] args) {
        SpringApplication.run(LoginSystemMain.class, args);
    }
}

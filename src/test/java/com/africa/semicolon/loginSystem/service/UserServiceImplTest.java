package com.africa.semicolon.loginSystem.service;

import com.africa.semicolon.loginSystem.data.repository.UserRepo;
import com.africa.semicolon.loginSystem.dtos.request.CreateUserRequest;
import com.africa.semicolon.loginSystem.dtos.response.CreateUserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataMongoTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

class UserServiceImplTest {

    @Autowired
    private UserRepo repository;
    @Autowired
    private UserService userService;

    @Test
    public void saveUserTest(){
        //given
        CreateUserRequest newUser = new CreateUserRequest();
        //when
        newUser.setFirstName("adeola");
        newUser.setLastName("oladeji");
        newUser.setPassword("deeDeji12");
        newUser.setUserName("deji101");


        userService.createUser(newUser);

//
        assertThat(userService.getAllUsers().size(),is(1));
        //assert
    }

    @Test
    public void testThatResponse_success(){
        //given
        CreateUserRequest newUser = new CreateUserRequest();

        newUser.setFirstName("adeola");
        newUser.setLastName("oladeji");
        newUser.setPassword("deeDeji12");
        newUser.setUserName("deji101");

        CreateUserResponse response = userService.createUser(newUser);
        assertThat(response.getFullName(), is("adeola oladeji"));
        assertThat(response.getMessage(), is("user registered"));

    }

    @Test
    public void testThatPasswordMustBeGreaterThan0rEqualTo8Characters(){

        //given
        CreateUserRequest newUser = new CreateUserRequest();

        newUser.setFirstName("adeola");
        newUser.setLastName("oladeji");
        newUser.setPassword("dee");
        newUser.setUserName("deji101");

        assertThrows(IllegalArgumentException.class, ()-> userService.createUser(newUser));

//        assertThat(userService.setPassword(),is(>8));

    }

}
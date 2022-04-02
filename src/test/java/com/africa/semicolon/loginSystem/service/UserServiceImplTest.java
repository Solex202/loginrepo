package com.africa.semicolon.loginSystem.service;

import com.africa.semicolon.loginSystem.data.model.User;
import com.africa.semicolon.loginSystem.data.repository.UserRepo;
import com.africa.semicolon.loginSystem.dtos.request.UserRequest;
import com.africa.semicolon.loginSystem.dtos.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;


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
        UserRequest newUser = new UserRequest();
        //when
        newUser.setFirstName("adeola");
        newUser.setLastName("oladeji");
        newUser.setPassword("deeDeji");
        newUser.setUserName("deji101");


        userService.createUser(newUser);

//
        assertThat(userService.getAllUsers().size(),is(1));
        //assert
    }

    @Test
    public void testThatResponse_success(){
        //given
        UserRequest newUser = new UserRequest();

        newUser.setFirstName("adeola");
        newUser.setLastName("oladeji");
        newUser.setPassword("deeDeji");
        newUser.setUserName("deji101");

        UserResponse response = userService.createUser(newUser);
        assertThat(response.getFullName(), is("adeola oladeji"));
        assertThat(response.getMessage(), is("user registered"));

    }

}
package com.africa.semicolon.loginSystem.service;

import com.africa.semicolon.loginSystem.data.repository.UserRepo;
import com.africa.semicolon.loginSystem.dtos.request.CreateUserRequest;
import com.africa.semicolon.loginSystem.dtos.request.LoginRequest;
import com.africa.semicolon.loginSystem.dtos.request.UpdateRequest;
import com.africa.semicolon.loginSystem.dtos.response.CreateUserResponse;
import com.africa.semicolon.loginSystem.dtos.response.FindUserResponse;
import com.africa.semicolon.loginSystem.dtos.response.LoginResponse;
import com.africa.semicolon.loginSystem.dtos.response.UpdateResponse;
import com.africa.semicolon.loginSystem.exception.*;
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
    public void testThatPasswordMustBeGreaterThan0rEqualTo8Characters_throwException(){

        //given
        CreateUserRequest newUser = new CreateUserRequest();

        newUser.setFirstName("adeola");
        newUser.setLastName("oladeji");
        newUser.setPassword("dee");
        newUser.setUserName("deji101");

        assertThrows(InvalidPasswordException.class, ()-> userService.createUser(newUser));
    }

       @Test
    public void testThat_A_UserCannotBeRegisteredTwice_throwException(){
        //given
           CreateUserRequest newUser = new CreateUserRequest();
           newUser.setFirstName("adeola");
           newUser.setLastName("oladeji");
           newUser.setPassword("deeDeji12");
           newUser.setUserName("deji101");

           userService.createUser(newUser);
           //given
           CreateUserRequest anotherUser = new CreateUserRequest();
           anotherUser.setFirstName("adeola");
           anotherUser.setLastName("oladeji");
           anotherUser.setPassword("deeDeji12");
           anotherUser.setUserName("deji101");

           assertThrows(UserAlreadyExistsException.class,()-> userService.createUser(anotherUser));
    }

       @Test
       public void testThatUserCanLoginWithUsernameAndPassword(){
           //given
           CreateUserRequest newUser = new CreateUserRequest();
           newUser.setFirstName("adeola");
           newUser.setLastName("oladeji");
           newUser.setUserName("deji101");
           newUser.setPassword("deeDeji12");

           userService.createUser(newUser);
           LoginRequest loginRequest = new LoginRequest("deji101", "deeDeji12");
           LoginResponse response = userService.login(loginRequest);
           assertThat(response.getMessage(), is("LoginRequest successfull"));
    }

       @Test
        public void testThatUserCannotLoginWithIncorrectPassword_throwException(){
           //given
           CreateUserRequest newUser = new CreateUserRequest();
           newUser.setFirstName("adeola");
           newUser.setLastName("oladeji");
           newUser.setPassword("deeDeji12");
           newUser.setUserName("deji101");

           userService.createUser(newUser);
           LoginRequest loginRequest = new LoginRequest("deji101", "mememe234");
           assertThrows(IncorrectPasswordException.class, ()-> userService.login(loginRequest));
       }

       @Test
    public void testThatUserCannotLoginWithIncorrectUsername_throwException(){
        //given
        CreateUserRequest newUser = new CreateUserRequest();
        newUser.setFirstName("adeola");
        newUser.setLastName("oladeji");
        newUser.setPassword("deeDeji12");
        newUser.setUserName("deji101");

        userService.createUser(newUser);
        LoginRequest loginRequest = new LoginRequest("lotachi123", "deeDeji12");
//           LoginResponse response = userService.login(loginRequest);
        assertThrows(IncorrectUsernameException.class, ()-> userService.login(loginRequest));
    }

    @Test
    public void usernameAlreadyExist_throwException(){
        //given
        CreateUserRequest newUser = new CreateUserRequest();
        newUser.setFirstName("adeola");
        newUser.setLastName("oladeji");
        newUser.setPassword("deeDeji12");
        newUser.setUserName("deji101");

        userService.createUser(newUser);
        //given
        CreateUserRequest anotherUser = new CreateUserRequest();
        anotherUser.setFirstName("dami");
        anotherUser.setLastName("johnson");
        anotherUser.setPassword("damidami");
        anotherUser.setUserName("deji101");

        assertThrows(UsernameAlreadyExistsException.class,()-> userService.createUser(anotherUser));

    }

    @Test
    public void testFindBy(){
        //given
        CreateUserRequest newUser = new CreateUserRequest();
        newUser.setFirstName("adeola");
        newUser.setLastName("oladeji");
        newUser.setPassword("deeDeji12");
        newUser.setUserName("deji101");

        userService.createUser(newUser);
        //given
        CreateUserRequest anotherUser = new CreateUserRequest();
        anotherUser.setFirstName("dami");
        anotherUser.setLastName("johnson");
        anotherUser.setPassword("damidami");
        anotherUser.setUserName("johnson202");
        userService.createUser(anotherUser);

        FindUserResponse response = userService.findByUserName("johnson202");
        assertThat(response.getFirstName(),is("dami"));
        assertThat(response.getLastName(),is("johnson"));
        assertThat(response.getUserName(),is("johnson202"));
    }

    @Test
    public  void testThatIfNoUserIsFound_throwException(){
        //given
        CreateUserRequest newUser = new CreateUserRequest();
        newUser.setFirstName("adeola");
        newUser.setLastName("oladeji");
        newUser.setPassword("deeDeji12");
        newUser.setUserName("deji101");

        userService.createUser(newUser);
        //given
        CreateUserRequest anotherUser = new CreateUserRequest();
        anotherUser.setFirstName("dami");
        anotherUser.setLastName("johnson");
        anotherUser.setPassword("damidami");
        anotherUser.setUserName("johnson202");
        userService.createUser(anotherUser);

        assertThrows(UserNotFoundException.class, ()-> userService.findByUserName("lota"));
    }

//    @Test
//        public void testThatNonExistingUserCannotLogin_throwException(){
//
//        LoginRequest loginRequest = new LoginRequest("deji10", "deeDeji190");
//           //assert
//           assertThrows(UserNotFoundException.class, ()-> userService.login(loginRequest));
//       }

       @Test
    public void testThatUserCanUpdateUsername(){
           //given
           CreateUserRequest newUser = new CreateUserRequest();
           newUser.setFirstName("adeola");
           newUser.setLastName("oladeji");
           newUser.setUserName("deji101");
           newUser.setPassword("deeDeji12");

           userService.createUser(newUser);
           LoginRequest loginRequest = new LoginRequest("deji101", "deeDeji12");
           LoginResponse response = userService.login(loginRequest);
           assertThat(response.getMessage(), is("LoginRequest successfull"));

           UpdateRequest updateRequest = new UpdateRequest();
           UpdateResponse updateUsernameResponse = userService.updateUsername(updateRequest, "deeDeji12");
           updateRequest.setUserName("ginibby101");

           assertThat(updateUsernameResponse.getMsg(), is("username updated"));

       }



}
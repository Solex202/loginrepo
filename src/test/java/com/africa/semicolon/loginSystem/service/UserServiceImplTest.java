package com.africa.semicolon.loginSystem.service;

import com.africa.semicolon.loginSystem.data.repository.UserRepo;
import com.africa.semicolon.loginSystem.dtos.request.CreateUserRequest;
import com.africa.semicolon.loginSystem.dtos.request.LoginRequest;
import com.africa.semicolon.loginSystem.dtos.request.UpdateRequest;
import com.africa.semicolon.loginSystem.dtos.response.*;
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
        //assert
        assertThat(userService.getAllUsers().size(),is(1));
    }

//    @Test
//    public void deleteAllUsersTest(){
//        //given
//        CreateUserRequest newUser = new CreateUserRequest();
//        newUser.setFirstName("adeola");
//        newUser.setLastName("oladeji");
//        newUser.setPassword("deeDeji12");
//        newUser.setUserName("deji101");
//
//        userService.createUser(newUser);
//        //given
//        CreateUserRequest anotherUser = new CreateUserRequest();
//        anotherUser.setFirstName("dami");
//        anotherUser.setLastName("johnson");
//        anotherUser.setPassword("damidami");
//        anotherUser.setUserName("johnson202");
//        userService.createUser(anotherUser);
////        assertThat(userService.getAllUsers().size(),is(2));
//
//        DeleteResponse response = userService.deleteAllUsers();
//        assertThat(response.getMessage(),is("all users deleted"));
//    }

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
           assertThat(response.getMessage(), is("LoginRequest successful"));
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
           assertThat(response.getMessage(), is("LoginRequest successful"));

           UpdateRequest updateRequest = new UpdateRequest();
           updateRequest.setOldUsername("deji101");
           updateRequest.setNewUsername("ginika1");
           UpdateResponse updateResponse = userService.updateUsername( "deeDeji12",updateRequest);

           assertThat(updateResponse.getMsg(), is("username updated"));

       }

    @Test
    public void testThatUserCanUpdateFirstname(){
        //given
        CreateUserRequest newUser = new CreateUserRequest();
        newUser.setFirstName("adeola");
        newUser.setLastName("oladeji");
        newUser.setUserName("deji101");
        newUser.setPassword("deeDeji12");

        userService.createUser(newUser);
        LoginRequest loginRequest = new LoginRequest("deji101", "deeDeji12");
        LoginResponse response = userService.login(loginRequest);
        assertThat(response.getMessage(), is("LoginRequest successful"));

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setOldFirstName("adeola");
        updateRequest.setNewFirstName("mmesoma");
        updateRequest.setOldUsername("deji101");
        UpdateResponse updateResponse = userService.updateFirstName( "deeDeji12",updateRequest);

        assertThat(updateResponse.getMsg(), is("first name updated"));

    }

    @Test
    public void testThatUserCanUpdateLastname(){
        //given
        CreateUserRequest newUser = new CreateUserRequest();
        newUser.setFirstName("adeola");
        newUser.setLastName("oladeji");
        newUser.setUserName("deji101");
        newUser.setPassword("deeDeji12");

        userService.createUser(newUser);
        LoginRequest loginRequest = new LoginRequest("deji101", "deeDeji12");
        LoginResponse response = userService.login(loginRequest);
        assertThat(response.getMessage(), is("LoginRequest successful"));

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setOldLastName("adeola");
        updateRequest.setNewLastName("mmesoma");
        updateRequest.setOldUsername("deji101");
        UpdateResponse updateResponse = userService.updateLastName( "deeDeji12",updateRequest);

        assertThat(updateResponse.getMsg(), is("last name updated"));

    }

    @Test
    public void testThatUserCanUpdatePassword(){
        //given
        CreateUserRequest newUser = new CreateUserRequest();
        newUser.setFirstName("adeola");
        newUser.setLastName("oladeji");
        newUser.setUserName("deji101");
        newUser.setPassword("deeDeji12");

        userService.createUser(newUser);
        LoginRequest loginRequest = new LoginRequest("deji101", "deeDeji12");
        LoginResponse response = userService.login(loginRequest);
        assertThat(response.getMessage(), is("LoginRequest successful"));

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setOldPassword("deeDeji12");
        updateRequest.setNewPassword("dejideji201");
        updateRequest.setOldUsername("deji101");
        UpdateResponse updateResponse = userService.updatePassword( "deeDeji12",updateRequest);
//        dejideji201
        assertThat(updateResponse.getMsg(), is("password updated"));

    }

       @Test
    public void testThatUserCanBeDeleted(){
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

           DeleteResponse response = userService.deleteByUsername("johnson202");

           assertThat(response.getMessage(),is("user deleted"));
       }



}
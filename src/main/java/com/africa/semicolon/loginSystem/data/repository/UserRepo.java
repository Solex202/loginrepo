package com.africa.semicolon.loginSystem.data.repository;


import com.africa.semicolon.loginSystem.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface UserRepo extends MongoRepository<User,String> {

    User findByUserName(String userName);

    User findByUserNameAndPassword(String userName, String password);

    User findByPassword(String password);
}

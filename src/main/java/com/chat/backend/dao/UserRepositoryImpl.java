package com.chat.backend.dao;

import java.util.List;

import com.mongodb.client.result.UpdateResult;
import com.chat.backend.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@EnableAutoConfiguration
@Repository
@Qualifier("user")
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public User findOne(Query query) {
      return mongoTemplate.findOne(query,User.class);
    }

    @Override
    public User create(User newUser) {
       return mongoTemplate.insert(newUser,"User");
    }

    @Override
    public UpdateResult update(Query query, Update update) {
       return mongoTemplate.updateFirst(query,update, User.class);
    }
    
}

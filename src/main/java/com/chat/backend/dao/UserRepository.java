package com.chat.backend.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.chat.backend.model.User;

import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


public interface UserRepository {
    public User findOne(Query query);
    public User create(User newUser);
    public UpdateResult update(Query query,Update update);
}

package com.chat.backend.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chat.backend.model.Message;
import com.chat.backend.model.User;

import com.mongodb.client.result.UpdateResult;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


public interface ChatRepository {
    public List<Message> find(Query query);
    public List<User> findUsers(Query query);
    public Message create(Message msg);
    // public UpdateResult update(Query query,Update update);
}

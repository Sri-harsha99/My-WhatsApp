package com.chat.backend.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chat.backend.model.Message;
import com.chat.backend.model.User;

import com.mongodb.client.result.UpdateResult;

import java.util.List;
import com.chat.backend.model.Recent;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


public interface RecentRepository {
    public List<Recent> find(Query query);
    public Recent create(Recent recent);
    public UpdateResult update(Query query, Update update);    
    // public UpdateResult update(Query query,Update update);
}

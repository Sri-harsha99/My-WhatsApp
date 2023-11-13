package com.chat.backend.dao;

import java.util.List;

import com.mongodb.client.result.UpdateResult;
import com.chat.backend.model.Message;
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
@Qualifier("chat")
public class ChatRepositoryImpl implements ChatRepository{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Message> find(Query query) {
      return mongoTemplate.find(query,Message.class);
    }

   @Override
    public List<User> findUsers(Query query) {
      return mongoTemplate.find(query,User.class);
    }

    @Override
    public Message create(Message msg) {
       return mongoTemplate.insert(msg,"Message");
    }

   //  @Override
   //  public UpdateResult update(Query query, Update update) {
   //     return mongoTemplate.updateFirst(query,update, User.class);
   //  }
    
}

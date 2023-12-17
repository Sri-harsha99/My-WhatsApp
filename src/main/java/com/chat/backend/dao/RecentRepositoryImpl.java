package com.chat.backend.dao;

import java.util.List;

import com.mongodb.client.result.UpdateResult;
import com.chat.backend.model.Message;
import com.chat.backend.model.Recent;
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
public class RecentRepositoryImpl implements RecentRepository{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Recent> find(Query query) {
      return mongoTemplate.find(query,Recent.class);
    }

   @Override
    public Recent create(Recent recent){
      return mongoTemplate.insert(recent,"Recent");
    }

    @Override
    public UpdateResult update(Query query, Update update) {
       return mongoTemplate.updateFirst(query, update, Recent.class);
    }

   //  @Override
   //  public UpdateResult update(Query query, Update update) {
   //     return mongoTemplate.updateFirst(query,update, User.class);
   //  }
    
}

package com.chat.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.chat.backend.dao.ChatRepository;
import com.chat.backend.dao.UserRepository;
import com.chat.backend.model.Message;
import com.chat.backend.model.User;
import com.mongodb.client.result.UpdateResult;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@Service
public class chatServiceImpl implements chatService {  
    private final Logger Log = LoggerFactory.getLogger(getClass());

    @Autowired 
    ChatRepository chatRepository;

    @Override
    public List<Message> getMessages(Map<String,String> paramMap){
        Query query = new Query();
        Criteria criteria = new Criteria();

        // Add filters to the Criteria
        criteria.and("to").is(paramMap.get("to"));
        criteria.and("from").is(paramMap.get("from"));

        query.addCriteria(criteria);
        List<Message> result = chatRepository.find(query);
        
        return result;
    }


    @Override
    public String sendMessage(Message msg){
        
        Message createdMsg = chatRepository.create(msg);
        if (createdMsg.get_id() != null){
            return "True";
        }else{
            return "False";
        }
    }
    
    // @Override
    // public String sendImg(Map<String,String> paramMap);
    
    // public String searchMessages(Map<String,String> paramMap);
    
    @Override
    public List<User> searchUsers(Map<String,String> paramMap){
        Query query = new Query();
        Criteria criteria = new Criteria();

        // Add filters to the Criteria
        paramMap.put("name", paramMap.get("name")+"*");
        criteria.and("name").regex(paramMap.get("name"));


        query.addCriteria(criteria);
        List<User> result = chatRepository.findUsers(query);
        
        return result;
    }
}


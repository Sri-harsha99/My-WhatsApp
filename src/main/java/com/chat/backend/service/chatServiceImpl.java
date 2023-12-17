package com.chat.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.ArrayUtils;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import com.chat.backend.model.Recent;
import com.chat.backend.dao.ChatRepository;
import com.chat.backend.dao.UserRepository;
import com.chat.backend.model.Message;
import com.chat.backend.model.User;
import com.mongodb.client.result.UpdateResult;
import com.chat.backend.dao.RecentRepository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.kafka.core.KafkaTemplate;

@Service
public class chatServiceImpl implements chatService {  
    private final Logger Log = LoggerFactory.getLogger(getClass());

    @Autowired 
    ChatRepository chatRepository;

    @Autowired 
    RecentRepository recentRepository;

    @Autowired KafkaProducer kafkaProducer;

    @Override
    public List<Message> getMessages(Map<String,String> paramMap){
        Query query1 = new Query();
        Criteria criteria1 = new Criteria();

        criteria1.and("to").is(paramMap.get("to"));
        criteria1.and("from").is(paramMap.get("from"));

        query1.addCriteria(criteria1);
        List<Message> result1 = chatRepository.find(query1);

        Query query2 = new Query();
        Criteria criteria2 = new Criteria();

        criteria2.and("from").is(paramMap.get("to"));
        criteria2.and("to").is(paramMap.get("from"));

        query2.addCriteria(criteria2);
        List<Message> result2 = chatRepository.find(query2);
        result1.addAll(result2);
        return result1;
    }


    @Override
    public String sendMessage(Message msg){
        
        Message createdMsg = chatRepository.create(msg);

        Query query = new Query();
        query.addCriteria(Criteria.where("userEmail").is(msg.getTo()));
        query.addCriteria(Criteria.where("fromEmail").is(msg.getFrom()));


        List<Recent> existingDoc = recentRepository.find(query);

        if (existingDoc != null) {
            Update update = new Update()
                    .set("msg", msg.getMsg())
                    .set("time", msg.getTime());

            recentRepository.update(query, update);
            System.out.println("done1");
        } else {
            Recent newData = new Recent(msg.getMsg(), msg.getTime(), msg.getTo(), msg.getFromName(), msg.getFrom());
            recentRepository.create(newData);

            System.out.println("done");
        }

        query = new Query();
        query.addCriteria(Criteria.where("userEmail").is(msg.getFrom()));
        query.addCriteria(Criteria.where("fromEmail").is(msg.getTo()));

        existingDoc = recentRepository.find(query);

        if (existingDoc != null) {
            Update update = new Update()
                    .set("msg", msg.getMsg())
                    .set("time", msg.getTime());

            recentRepository.update(query, update);
            System.out.println("done1");
        } else {
            Recent newData = new Recent(msg.getMsg(), msg.getTime(), msg.getFrom(), msg.getFromName(), msg.getTo());
            recentRepository.create(newData);

            System.out.println("done");
        }

        kafkaProducer.sendMessage(msg.toString());



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
        paramMap.put("data", paramMap.get("data"));
        Pattern pattern = Pattern.compile(paramMap.get("data"), Pattern.CASE_INSENSITIVE);

        query.addCriteria(Criteria.where("name").regex(pattern));

        List<User> result = chatRepository.findUsers(query);
        
        return result;
    }

    @Override
    public List<Recent> getRecent(Map<String,String> paramMap){

        Query query = new Query();
        query.addCriteria(Criteria.where("userEmail").is(paramMap.get("userEmail")));

        List<Recent> result = recentRepository.find(query);
        
        return result;
    }
}


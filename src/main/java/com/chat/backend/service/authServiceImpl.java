package com.chat.backend.service;

import org.apache.kafka.common.config.types.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.chat.backend.dao.UserRepository;
import com.chat.backend.model.User;
import com.mongodb.client.result.UpdateResult;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class authServiceImpl implements authService {  
    private final Logger Log = LoggerFactory.getLogger(getClass());
    @Autowired 
    UserRepository userRepository;

    @Override
    public User loginUser(Map<String, String> paramMap){
    
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(paramMap.get("email")));
    
        User user = userRepository.findOne(query);
        if(user == null){
            System.out.println("no mail");
            return null; //"No user with given mail"
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(paramMap.get("password"), user.getPassword())) {
            System.out.println("Password matches");
            return user;
        } else {
            System.out.println("Password does not match");
        }

        
        System.out.println("wrong password");
        return null; //"Wrong password"
    }

    private User getUser(Map<String, String> paramMap){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(paramMap.get("email")));
    
        User user = userRepository.findOne(query);
        return user;
    }

    
    
    
    public User signUp(User newUser){
        Map<String,String> paramMap = new HashMap<>();

        paramMap.put("email" , newUser.getEmail());
        User checkUser = getUser(paramMap);

        if(checkUser != null){
            Log.info("User with given email already exists");
            return null;
        }

        newUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
        
        User createdUser = userRepository.create(newUser);

        return createdUser;
    }

    public String forgotPassword1(String email){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));


        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("email" , email);
        User currentUser = getUser(paramMap);
        Integer randomNumber = ThreadLocalRandom.current().nextInt(100000, 1000000);
        Update update = new Update().set("otp", randomNumber.toString());
        UpdateResult result = userRepository.update(query, update);
        
        return "Password has been sent to your mail";
    }

    public String forgotPassword2(String email,String otp){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        
        User user = userRepository.findOne(query);
        String savedOtp = user.getOtp();

        if(savedOtp.equals(otp)){
            return "true";
        }else{
            return "Wrong OTP";
        }
    }

    public User forgotPassword3(String email,String password){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        
        Update update = new Update().set("password", password);
        UpdateResult result = userRepository.update(query, update);

        User user = userRepository.findOne(query);
        return user;
    }
}


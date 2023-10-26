package com.chat.backend.service;

import org.springframework.stereotype.Service;
import com.chat.backend.model.User;

@Service
public class authService {
    
    User temp = new User(0, "Harsha", "sriharsha852@gmail.com", "password", "Dallas");

    public User login(User user){
        System.out.println(user.name);
        return temp;
    }
    
    
    public User signUp(User user){
        
        System.out.println("in login service");
        return temp;
    }

    public String forgotPassword(){

        return "Password has been sent to your mail";
    }

    public User otpVerify(){
        System.out.println("in login service");
        
        return temp;
    }

}

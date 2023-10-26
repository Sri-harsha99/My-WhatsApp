package com.chat.backend.service;

import org.springframework.stereotype.Service;

@Service
public class chatService {
    
    
    public String sendMessage(){
        return "Msg sent";
    } 

    public String getMessages(){
        return "got messages";
    }

    public String sendImg(){
        return "img sent";
    }

    public String LiveChat(){
        return "live chat";
    }
}

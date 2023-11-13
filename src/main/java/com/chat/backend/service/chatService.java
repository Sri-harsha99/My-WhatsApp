package com.chat.backend.service;

import java.util.List;
import java.util.Map;

import com.chat.backend.model.Message;
import com.chat.backend.model.User;

public interface chatService {

    public List<Message> getMessages(Map<String,String> paramMap);
    public String sendMessage(Message msg);
    // public String sendImg(Map<String,String> paramMap);
    // public String liveChat(Map<String,String> paramMap);
    // public String searchMessages(Map<String,String> paramMap);
    public List<User> searchUsers(Map<String,String> paramMap);
    
    // public Message editMessage(Map<String,String> paramMap);

}

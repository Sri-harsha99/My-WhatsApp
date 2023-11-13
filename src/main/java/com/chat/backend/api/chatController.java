package com.chat.backend.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chat.backend.model.Message;
import com.chat.backend.model.User;
import com.chat.backend.service.chatService;

@RestController
@RequestMapping(value = "/")
public class chatController {
    
    @Autowired
    private chatService chatService;

    @CrossOrigin
    @RequestMapping("/sendMsg")
    public String sendMessage(@RequestBody Message message){
        return chatService.sendMessage(message);
    }

    @CrossOrigin
    @RequestMapping("/getMsg")
    public List<Message> getMessages(@RequestBody Map<String,String> paramMap){
        return chatService.getMessages(paramMap);
    }

    // @CrossOrigin
    // @RequestMapping("/sendImg")
    // public String sendImg(@RequestBody Map<String,String> paramMap){
    //     return chatService.sendImg(paramMap);
    // }
    
    // @CrossOrigin
    // @RequestMapping("/liveChat")
    // public String liveChat(@RequestBody Map<String,String> paramMap){
    //     return chatService.liveChat(paramMap);
    // }

    @CrossOrigin
    @RequestMapping("/searchUsers")
    public List<User> searchUsers(@RequestBody Map<String,String> paramMap){
        return chatService.searchUsers(paramMap);
    }

    // @CrossOrigin
    // @RequestMapping("/searchMessages")
    // public String searchMessages(@RequestBody Map<String,String> paramMap){
    //     return chatService.searchMessages(paramMap);
    // }
}

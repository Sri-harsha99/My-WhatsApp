package com.chat.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chat.backend.service.chatService;

@RestController
public class chatController {
    
    @Autowired
    private chatService chatService;

    @CrossOrigin
    @RequestMapping("/sendMsg")
    public String sendMessage(){
        return chatService.sendMessage();
    }

    @CrossOrigin
    @RequestMapping("/getMsg")
    public String getMessages(){
        return chatService.sendMessage();
    }

    @CrossOrigin
    @RequestMapping("/sendImg")
    public String sendImg(){
        return chatService.sendMessage();
    }
    
    @CrossOrigin
    @RequestMapping("/liveChat")
    public String liveChat(){
        return chatService.sendMessage();
    }
}

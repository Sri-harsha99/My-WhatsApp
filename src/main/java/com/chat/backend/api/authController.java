package com.chat.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.chat.backend.model.User;
import com.chat.backend.service.authService;

@RestController
public class authController {
    
    @Autowired
    private authService authService;
    
    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User userLogin(@RequestBody User user){
        return authService.login(user);
    }
    @CrossOrigin
    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public String userRegister(@RequestBody User user){
        return "Sign up";
        // return authService.signUp(user);
    }
    @CrossOrigin
    @RequestMapping(value="/forgotPassword", method = RequestMethod.POST)
    public String forgotPassword(){
        return authService.forgotPassword();
    }
    @CrossOrigin
    @RequestMapping(value="/otpVerify", method = RequestMethod.POST)
    public User otpVerify(){
        return authService.otpVerify();
    }
}

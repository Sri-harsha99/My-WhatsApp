package com.chat.backend.api;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.chat.backend.dao.UserRepository;
import com.chat.backend.model.User;
import com.chat.backend.service.authService;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
@RestController
@RequestMapping(value = "/")
public class authController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private authService authService;
    
    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User userLogin(@RequestHeader HttpHeaders headers,@RequestParam Map<String,String> paramMap){
        LOG.info("Saving user.");
        User res = authService.loginUser(paramMap);
        System.out.println(res);
        return res;
    }

    
    @CrossOrigin
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @PostMapping(consumes = "application/json")
    public User userRegister(@RequestHeader HttpHeaders headers,@RequestParam Map<String,String> paramMap, @RequestBody User newUser){
        LOG.info("Saving user.");
        User res = authService.signUp(newUser);
        System.out.println(res);
        return res;
    }

    @CrossOrigin
    @RequestMapping(value="/forgotPassword1", method = RequestMethod.POST)
    public String forgotPassword(@RequestBody Map<String,String> paramMap){
        return authService.forgotPassword1(paramMap.get("email"));
    }

    @CrossOrigin
    @RequestMapping(value="/forgotPassword2", method = RequestMethod.POST)
    public String forgotPassword2(@RequestBody Map<String,String> paramMap){
        return authService.forgotPassword2(paramMap.get("email"),paramMap.get("otp"));
    }

    @CrossOrigin
    @RequestMapping(value="/forgotPassword3", method = RequestMethod.POST)
    public User forgotPassword3(@RequestBody Map<String,String> paramMap){
        return authService.forgotPassword3(paramMap.get("email"),paramMap.get("password"));
    }

}

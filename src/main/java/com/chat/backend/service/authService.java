package com.chat.backend.service;

import java.util.List;
import java.util.Map;

import com.chat.backend.model.User;

public interface authService {

    public User loginUser(Map<String,String> user);
    public User signUp(User user);
    public String forgotPassword1(String email);
    public String forgotPassword2(String email,String otp);
    public User forgotPassword3(String email,String password);
    
    // public InstantProduct getInstantProductById(String instantProductId);
    // public InstantProduct createInstantProduct(InstantProduct instantProduct,Boolean checkDuplicate);
    // public InstantProduct updateInstantProduct(Map<String,Object> instantProduct);
    // public Boolean deleteInstantProduct(Map<String,Object> instantProduct);

}

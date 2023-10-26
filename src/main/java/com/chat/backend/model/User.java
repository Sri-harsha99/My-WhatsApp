package com.chat.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
public class User {
    @Id
    public int id;
    public String name;
    public String email;
    public String password;
    public String city;
    
    public User(int id, String name, String email, String password, String city){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.city = city;
    }

}

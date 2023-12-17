package com.chat.backend.model;

import java.lang.reflect.Array;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import com.chat.backend.model.Recent;

@Data        
@Document("User")       
public class User {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId _id;
    private String email;
    private String password;
    private String city;
    private String name;
    private String otp;
}

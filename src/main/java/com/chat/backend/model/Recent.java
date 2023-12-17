package com.chat.backend.model;

import java.lang.reflect.Array;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data        
@Document("Recent")
public class Recent {
    String userEmail;
    String msg;
    String name;
    String time;
    String fromEmail;

    public Recent(String msg, String time, String userEmail, String name, String fromEmail) {
        this.msg = msg;
        this.time = time;
        this.userEmail = userEmail;
        this.name = name;
        this.fromEmail = fromEmail;
    }
}

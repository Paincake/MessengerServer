package com.example.messengerserver.cache;

import com.example.messengerserver.entity.Message;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Component
public class MessageCache {
    ConcurrentHashMap<Long, TreeMap<Long, Message>> cachedChats;
//    public void saveChat(){
//
//    }
//    public boolean containsChat(){
//
//    }
//    public void addMessage(){
//
//    }
//    public void deleteMessage(){
//
//    }
}

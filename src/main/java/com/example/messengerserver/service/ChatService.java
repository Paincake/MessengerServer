package com.example.messengerserver.service;

import com.example.messengerserver.entity.AuthUser;
import com.example.messengerserver.entity.Chat;
import com.example.messengerserver.entity.Message;
import com.example.messengerserver.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.xml.Jaxb2CollectionHttpMessageConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ChatService {
    @Autowired
    ChatRepository chatRepository;
    public Chat findChatByUser(AuthUser user){
        return chatRepository.findChatByUsersContaining(user);
    }
    public List<Chat> findAllChatsByUser(AuthUser user){
        return chatRepository.findChatsByUsersContaining(user);
    }

   public Chat findChatById(Long id){
        return chatRepository.findById(id).orElse(null);
   }
    public Chat findChatByUsers(Set<AuthUser> user){
        return chatRepository.findChatByUsersIn(List.of(user));
    }

    public void createChat(Chat chat){
        chatRepository.save(chat);
    }
    public void deleteChat(Long chatId){
        if(chatRepository.existsById(chatId)) chatRepository.deleteById(chatId);
    }
}


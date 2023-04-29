package com.example.messengerserver.service;

import com.example.messengerserver.entity.Chat;
import com.example.messengerserver.entity.Message;
import com.example.messengerserver.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    @Autowired
    ChatRepository chatRepository;

    public void createChat(Chat chat){
        if(!chatRepository.findById(chat.getId()).isPresent()) chatRepository.save(chat);
    }
    public void deleteChat(Long chatId){
        if(chatRepository.existsById(chatId)) chatRepository.deleteById(chatId);
    }
}


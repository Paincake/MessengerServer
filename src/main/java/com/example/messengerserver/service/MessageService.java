package com.example.messengerserver.service;

import com.example.messengerserver.entity.Message;
import com.example.messengerserver.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public void sendMessage(Message message){
        messageRepository.save(message);
    }

    public void deleteMessage(Long messageId){
        if(messageRepository.findById(messageId).isPresent()) messageRepository.deleteById(messageId);
    }
}

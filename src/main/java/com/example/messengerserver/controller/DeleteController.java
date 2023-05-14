package com.example.messengerserver.controller;

import com.example.messengerserver.repository.ChatRepository;
import com.example.messengerserver.repository.FormRepository;
import com.example.messengerserver.repository.MessageRepository;
import com.example.messengerserver.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/delete")
public class DeleteController {
    @Autowired
    FormRepository formRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    MessageRepository messageRepository;

    @GetMapping("")
    public String deleter(){
        formRepository.deleteAll();
        replyRepository.deleteAll();
        messageRepository.deleteAll();
        chatRepository.deleteAll();

        return "deleted";
    }
}

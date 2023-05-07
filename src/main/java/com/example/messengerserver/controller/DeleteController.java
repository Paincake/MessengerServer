package com.example.messengerserver.controller;

import com.example.messengerserver.repository.ChatRepository;
import com.example.messengerserver.repository.FormRepository;
import com.example.messengerserver.repository.ReplyRepository;
import com.example.messengerserver.service.ChatService;
import com.example.messengerserver.service.FormService;
import com.example.messengerserver.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("")
    public String deleter(){
        formRepository.deleteAll();
        replyRepository.deleteAll();
        chatRepository.deleteAll();
        return "deleted";
    }
}

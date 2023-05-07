package com.example.messengerserver.controller;

import com.example.messengerserver.entity.AuthUser;
import com.example.messengerserver.entity.Chat;
import com.example.messengerserver.entity.Message;
import com.example.messengerserver.service.ChatService;
import com.example.messengerserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private ConcurrentHashMap<Long, TreeMap<Long, Message>> cachedChats;
    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @GetMapping("/{chatId}")
    public String getChat(@PathVariable String chatId, Model model){
        model.addAttribute("chat", chatService.findChatById(Long.parseLong(chatId)));
        return "chat";
    }

//    @PostMapping("/{chatId}")
//    public String sendMessage(@PathVariable String chatId){
//
//    }

    @GetMapping("/all/{userId}")
    public String getAllChats(@PathVariable String userId, Model model){
        model.addAttribute("chats", chatService.findAllChatsByUser(userService.findUserById(Long.parseLong(userId))));
        return "chats";
    }

    @GetMapping("/new/{userId}")
    public String createChat(@PathVariable String userId){
        AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Chat chat = new Chat();
        chat.setPrivate(false);
        chat.setUsers(List.of(userService.findUserById(Long.parseLong(userId)), user));
        chatService.createChat(chat);
        return String.format("redirect:/chat/%s", chat.getId());
    }
}

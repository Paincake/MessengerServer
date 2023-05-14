package com.example.messengerserver.controller;

import com.example.messengerserver.entity.AuthUser;
import com.example.messengerserver.entity.Chat;
import com.example.messengerserver.entity.Message;
import com.example.messengerserver.service.ChatService;
import com.example.messengerserver.service.MessageService;
import com.example.messengerserver.service.ReplyService;
import com.example.messengerserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;
    @Autowired
    private ReplyService replyService;

    @GetMapping("/{chatId}")
    public String getChat(@PathVariable String chatId, Model model) {
        Chat chat = chatService.findChatById(Long.parseLong(chatId));
        AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!Objects.equals(chat.getUsers().get(0).getId(), user.getId()) && !Objects.equals(chat.getUsers().get(1).getId(), user.getId())){
            return String.format("redirect:/all/%s", user.getId());
        }
        else{
            if(chat != null){
                model.addAttribute("chat", chat);
                model.addAttribute("chatId", chatId);
                model.addAttribute("userId", user.getId());
                model.addAttribute("username", user.getUsername());
                return "chat";
            }
            else{
                model.addAttribute("error", "Чат не найден. Возможно, он был удалён другим пользователем");
                return "error";
            }
        }
    }
    @PostMapping("/{chatId}/send")
    public String sendMessage(@PathVariable String chatId,
                              @ModelAttribute(name = "message") Message message,
                              Model model){
        Chat chat = chatService.findChatById(Long.parseLong(chatId));
        message.setSender((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(Objects.equals(chat.getUsers().get(0).getId(), message.getSender().getId())){
            message.setReceiver(chat.getUsers().get(1));
        }
        else{
            message.setReceiver(chat.getUsers().get(0));
        }
        message.setSendingTime(LocalDateTime.now());
        message.setForwarded(false);
        message.setChat(chat);
        messageService.sendMessage(message);
        return String.format("redirect:/chat/%s", chatId);
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
    @GetMapping("/new/private/{userId}/{replyId}")
    public String createChatFromReply(@PathVariable String userId, @PathVariable String replyId){
        AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        replyService.deleteReply(Long.parseLong(replyId));
        Chat chat = new Chat();
        chat.setPrivate(false);
        chat.setUsers(List.of(userService.findUserById(Long.parseLong(userId)), user));
        chatService.createChat(chat);
        return String.format("redirect:/chat/%s", chat.getId());
    }
}

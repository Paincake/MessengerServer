package com.example.messengerserver.controller;

import com.example.messengerserver.entity.AuthUser;
import com.example.messengerserver.entity.Chat;
import com.example.messengerserver.entity.Reply;
import com.example.messengerserver.service.ChatService;
import com.example.messengerserver.service.FormService;
import com.example.messengerserver.service.ReplyService;
import com.example.messengerserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    UserService userService;
    @Autowired
    ChatService chatService;
    @Autowired
    ReplyService replyService;
    @Autowired
    FormService formService;
    @GetMapping("")
    public String getAccount(Model model){
        AuthUser principal = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser user = userService.findUserById(principal.getId());
        Map<Chat, AuthUser> receiverMap = new HashMap<>();
        for(Chat chat : user.getChats()){
            AuthUser receiver;
            if(chat.getUsers().get(0).getId() != user.getId()){
                receiver = chat.getUsers().get(0);
            }
            else {
                receiver = chat.getUsers().get(1);
            }
            receiverMap.put(chat, receiver);
        }
        model.addAttribute("user", user);
        model.addAttribute("receivers", receiverMap);
        model.addAttribute("chats", user.getChats());
        model.addAttribute("forms", user.getForms());
        model.addAttribute("replies", user.getReplies());
        return "account";
    }
    @GetMapping("/{userId}/{formId}/replies")
    public String getAllReplies(@PathVariable String formId, @PathVariable String userId, Model model){
        AuthUser principal = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal.getId() != Long.parseLong(userId)){
            return "redirect:/account";
        }
        List<Reply> replyList = formService.findAllRepliesToForm(Long.parseLong(formId));
        if(replyList == null) return "redirect:/account";
        model.addAttribute("replies", replyList);
        return "form_replies";
    }
    @GetMapping("/{userId}/{formId}/delete/form")
    public String deleteForm(@PathVariable String userId, @PathVariable String formId){
        AuthUser principal = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal.getId() != Long.parseLong(userId)){
            return "redirect:/account";
        }
        if(formService.findFormById(Long.parseLong(formId)) != null){
            formService.deleteForm(Long.parseLong(formId));
        }
        return "redirect:/account";
    }
    @GetMapping("/{userId}/{chatId}/delete/chat")
    public String deleteChat(@PathVariable String userId, @PathVariable String chatId){
        AuthUser principal = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal.getId() != Long.parseLong(userId)){
            return "redirect:/account";
        }
        if(chatService.findChatById(Long.parseLong(chatId)) != null){
            chatService.deleteChat(Long.parseLong(chatId));
        }
        return "redirect:/account";
    }
    @GetMapping("/{userId}/{replyId}/delete/reply")
    public String deleteReply(@PathVariable String userId, @PathVariable String replyId){
        AuthUser principal = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal.getId() != Long.parseLong(userId)){
            return "redirect:/account";
        }
        if(replyService.findReplyById(Long.parseLong(replyId)) != null){
            replyService.deleteReply(Long.parseLong(replyId));
        }
        return "redirect:/account";
    }
}

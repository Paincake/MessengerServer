package com.example.messengerserver.controller;

import com.example.messengerserver.entity.AuthUser;
import com.example.messengerserver.entity.Chat;
import com.example.messengerserver.entity.Form;
import com.example.messengerserver.entity.Reply;
import com.example.messengerserver.service.ChatService;
import com.example.messengerserver.service.FormService;
import com.example.messengerserver.service.ReplyService;
import com.example.messengerserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

@Controller
@RequestMapping("/main")
public class MainPageController {
    @Autowired
    UserService userService;

    @Autowired
    FormService formService;

    @Autowired
    ReplyService replyService;

    @Autowired
    ChatService chatService;

    @GetMapping("")
    public String getMainPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getPrincipal().getClass().getSimpleName();
        if(name.equals("String")) {
            return "main_page";
        }
        AuthUser principal = (AuthUser) auth.getPrincipal();
        model.addAttribute("username", userService.findUserById(principal.getId()).getUsername());
        List<AuthUser> excluded = new ArrayList<>();

        List<Chat> userChats = chatService.findAllChatsByUser(principal);
        userChats.stream().map(Chat::getUsers).forEach(excluded::addAll);
        List<Reply> userReplies = replyService.findAllRepliesByUser(principal);
        userReplies
                .stream()
                .map(r -> r.getRepliedForm().getAuthor())
                .forEach(excluded::add);
        excluded.add(principal);
        List<Form> forms = formService.findAllForms(excluded);
        model.addAttribute("forms", forms);
        return "main_page_auth";
    }

    @GetMapping("/submit")
    public String getSubmitForm(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthUser principal = (AuthUser) auth.getPrincipal();
        List<Form> submittedForms = principal.getForms();
        if(submittedForms.size() == 0 || Period.between(
                submittedForms.get(submittedForms.size()-1).getSubmitTime().toLocalDate(),
                LocalDateTime.now().toLocalDate()).getDays() > 7){
            model.addAttribute("submitForm", new Form());
            return "submit_form";
        }
        else{
            return "redirect:/main";
        }
    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute("submitForm") Form form,
                             @RequestParam("isPrivate") String isPrivate,
                             Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthUser principal = (AuthUser) auth.getPrincipal();
        form.setAuthor(principal);
        form.setPrivate(!isPrivate.equals("f"));
        form.setSubmitTime(LocalDateTime.now());
        formService.submitForm(form);
        return "redirect:/main";
    }

    @GetMapping("/reply/{formId}")
    public String getReplyForm(@PathVariable String formId, Model model){
        Form form = formService.findFormById(Long.parseLong(formId));
        if(!form.isPrivate()){
            return String.format("redirect:/chat/new/%d", form.getAuthor().getId());
        }
        else{
            model.addAttribute("author", form.getAuthor());
            model.addAttribute("form", form);
            return "reply";
        }
    }

    @PostMapping("/reply/{formId}")
    public String replyForm(@PathVariable String formId,
                            @ModelAttribute("submitReply") Reply reply,
                            Model model){
        reply.setRepliedForm(formService.findFormById(Long.parseLong(formId)));
        reply.setRepliedUser((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        reply.setReplyDateTime(LocalDateTime.now());
        reply.setPictures(List.of(""));
        replyService.replyToForm(reply);
        return "redirect:/main";
    }

}


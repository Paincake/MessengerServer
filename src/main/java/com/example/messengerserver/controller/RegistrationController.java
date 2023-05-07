package com.example.messengerserver.controller;

import com.example.messengerserver.entity.AuthUser;
import com.example.messengerserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("authUserForm", new AuthUser());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("authUserForm") AuthUser authUserForm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "registration";
        }
        if(!authUserForm.getPassword().equals(authUserForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        if (!userService.saveUser(authUserForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }
        authUserForm.setAvatarImage("");
        return "redirect:/";
    }
}

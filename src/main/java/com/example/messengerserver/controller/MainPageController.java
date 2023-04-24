package com.example.messengerserver.controller;

import com.example.messengerserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/main")
    public String getMainPage(Model model){
        //model.addAttribute();
        return "main_page";
    }
}


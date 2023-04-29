package com.example.messengerserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ErrorController {
    @GetMapping("/error/{reason}")
    public String getError(@PathVariable String reason, Model model){
        model.addAttribute("reason", reason);
        return "error";
    }
}

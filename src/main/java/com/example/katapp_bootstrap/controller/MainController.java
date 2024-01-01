package com.example.katapp_bootstrap.controller;

import com.example.katapp_bootstrap.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showTemplate() {
        return "index";
    }
}

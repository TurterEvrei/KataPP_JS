package com.example.katapp_bootstrap.controller;

import com.example.katapp_bootstrap.enums.Role;
import com.example.katapp_bootstrap.entity.User;
import com.example.katapp_bootstrap.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUsers(Principal principal, Model model) {
        model.addAttribute("currentUser", userService.getUser(principal));
        return "user-page";
    }
}

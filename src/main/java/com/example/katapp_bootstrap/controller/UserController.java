package com.example.katapp_bootstrap.controller;

import com.example.katapp_bootstrap.entity.Role;
import com.example.katapp_bootstrap.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showUsers(Principal principal, Model model) {
        model.addAttribute("listUsers", userService.getListUsers());
        model.addAttribute("listRoles", Role.values());
        model.addAttribute("Admin", Role.ADMIN);
        model.addAttribute("User", Role.USER);
        model.addAttribute("currentUser", userService.getUser(principal));
        return "main-page";
    }
}

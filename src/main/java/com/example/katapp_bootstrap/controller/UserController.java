package com.example.katapp_bootstrap.controller;

import com.example.katapp_bootstrap.entity.User;
import com.example.katapp_bootstrap.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showTemplate() {
        return "index";
    }

    @GetMapping("/api/user")
    @ResponseBody
    public ResponseEntity<User> getProfile(Principal principal) {
        return ResponseEntity.ok(userService.getUser(principal));
    }
}

package com.example.katapp_bootstrap.controller;

import com.example.katapp_bootstrap.entity.User;
import com.example.katapp_bootstrap.enums.Role;
import com.example.katapp_bootstrap.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAdminPage(Principal principal, Model model) {
        model.addAttribute("listUsers", userService.getListUsers());
        model.addAttribute("listRoles", Role.values());
        model.addAttribute("Admin", Role.ADMIN);
        model.addAttribute("User", Role.USER);
        model.addAttribute("currentUser", userService.getUser(principal));
        model.addAttribute("newUser", new User());
        return "admin-page";
    }

    @PostMapping("user")
    public String addUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("user")
    public String editUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("user")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}

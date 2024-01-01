package com.example.katapp_bootstrap.controller;

import com.example.katapp_bootstrap.entity.User;
import com.example.katapp_bootstrap.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final UserService userService;

    public ApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<User> getProfile(Principal principal) {
        return ResponseEntity.ok(userService.getUser(principal));
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getListUsers());
    }

    @PostMapping("/admin")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PutMapping("/admin")
    public ResponseEntity<Boolean> editUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user) != null);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

}

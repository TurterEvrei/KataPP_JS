package com.example.katapp_bootstrap.service;

import com.example.katapp_bootstrap.entity.Role;
import com.example.katapp_bootstrap.entity.User;
import com.example.katapp_bootstrap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getListUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUser(Principal principal) {
        return userRepository.findByEmail(principal.getName()).orElse(null);
    }

    @Override
    public void saveUser(User user) {
        if (user.getId() != null && user.getPassword().isEmpty()) {
            user.setPassword(userRepository.findById(user.getId()).orElseThrow(() ->
                    new UsernameNotFoundException("Can`t find user with id:" + user.getId() + " for editing")).getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getRoles().isEmpty()) {
            user.setRoles(Stream.of(Role.USER).collect(Collectors.toSet()));
        }
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

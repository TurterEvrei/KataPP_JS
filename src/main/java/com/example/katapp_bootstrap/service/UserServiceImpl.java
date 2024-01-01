package com.example.katapp_bootstrap.service;

import com.example.katapp_bootstrap.enums.Role;
import com.example.katapp_bootstrap.entity.User;
import com.example.katapp_bootstrap.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
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
    public User saveUser(User user) {
        if (user.getId() != null && user.getPassword().isEmpty()) {
            user.setPassword(userRepository.findById(user.getId()).orElseThrow(() ->
                    new UsernameNotFoundException("Can`t find user with id:" + user.getId() + " for editing")).getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getRoles().isEmpty()) {
            user.setRoles(Stream.of(Role.USER).collect(Collectors.toSet()));
        }
        return userRepository.save(user);
    }

    @Override
    public Boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}

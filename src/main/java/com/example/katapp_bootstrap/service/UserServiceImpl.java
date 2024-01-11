package com.example.katapp_bootstrap.service;

import com.example.katapp_bootstrap.entity.Role;
import com.example.katapp_bootstrap.entity.User;
import com.example.katapp_bootstrap.enums.RoleType;
import com.example.katapp_bootstrap.repository.RoleRepository;
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
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
        if (user.getId() != null) {
            User storedUser = userRepository.findById(user.getId()).orElseThrow(() ->
                    new UsernameNotFoundException("Can`t find user with id:" + user.getId() + " for editing"));
            if (user.getPassword().isEmpty()) {
                user.setPassword(storedUser.getPassword());
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
//            storedUser.getRoles().forEach(role -> {
//                if (!user.getRoles().contains(role)) {
//                    roleRepository.deleteRole(role.getId());
//                }
//            });
//            roleRepository.deleteRole(44L);
        }
        if (user.getRoles().isEmpty()) {
            user.setRoles(Stream.of(new Role(RoleType.USER)).collect(Collectors.toSet()));
        }
        System.out.println(user);
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

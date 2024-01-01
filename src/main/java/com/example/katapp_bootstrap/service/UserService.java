package com.example.katapp_bootstrap.service;


import com.example.katapp_bootstrap.entity.User;

import java.security.Principal;
import java.util.List;

public interface UserService {
    List<User> getListUsers();
    User getUser(Long id);
    User getUser(Principal principal);
    User saveUser(User user);
    Boolean deleteUser(Long id);
}

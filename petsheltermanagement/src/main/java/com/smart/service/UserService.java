package com.smart.service;

import org.springframework.ui.Model;

import com.smart.entites.User;

public interface UserService {
    public User userRegister(User user, boolean agreement) throws Exception;

}

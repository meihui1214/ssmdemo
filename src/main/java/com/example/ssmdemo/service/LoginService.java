package com.example.ssmdemo.service;

import com.example.ssmdemo.domain.User;

import java.util.List;

public interface LoginService {
    public User login(String email, String password);
    public List<User> selectAll();
}

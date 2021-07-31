package com.example.ssmdemo.service;

import com.example.ssmdemo.domain.User;

public interface LoginService {
    public User login(Integer uId, String password);
    public User selectUIdByEmail(String email);
}

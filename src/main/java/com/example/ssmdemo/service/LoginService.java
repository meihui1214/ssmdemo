package com.example.ssmdemo.service;

import com.example.ssmdemo.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface LoginService {
    public User login(String email, String password, HttpServletRequest request, HttpServletResponse response);
    public List<User> selectAll();
}

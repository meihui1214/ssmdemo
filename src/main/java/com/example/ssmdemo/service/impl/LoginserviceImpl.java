package com.example.ssmdemo.service.impl;

import com.example.ssmdemo.domain.User;
import com.example.ssmdemo.mapper.UserMapper;
import com.example.ssmdemo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class LoginserviceImpl  implements LoginService {
    @Autowired
   private UserMapper userMapper;

    @Transactional
   public User login(String email, String password){
      if (userMapper.SelectByEmailAndPassword(email,password) != null){
          User user = new User();
          user.setLoginCount(0);
          LocalDateTime now =LocalDateTime.now();
          user.setLastLoginTime(now);
          userMapper.insert(user);
          System.out.println("用户登录成功");
          return user;
      }else {
          System.out.println("用户不存在");
      }
        System.out.println("dsfaf");
       return null;
   }

    @Override
    public List<User> selectAll() {
       return userMapper.select();
    }
}

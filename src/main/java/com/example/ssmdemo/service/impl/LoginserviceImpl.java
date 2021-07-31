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

@Service
@Transactional
public class LoginserviceImpl  implements LoginService {
    @Autowired
   private UserMapper userMapper;

   public User login(Integer uId, String password){
      if (userMapper.SelectByUIdAndPassword(uId,password) != null){
          User user = new User();
          user.setLoginCount(0);
          user.setUId(uId);
          user.setPassword(password);
          LocalDateTime now =LocalDateTime.now();
          user.setLastLoginTime(now);
          userMapper.insert(user);
          System.out.println("用户登录成功");
      }else {
          System.out.println("用户不存在");
      }
       return userMapper.SelectByUIdAndPassword(uId, password);
   }

   public User selectUIdByEmail(String email){
        return userMapper.selectUIdByEmail(email);
   }
}

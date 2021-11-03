package com.example.ssmdemo.service.impl;

import com.example.ssmdemo.domain.Log;
import com.example.ssmdemo.domain.User;
import com.example.ssmdemo.mapper.LogMapper;
import com.example.ssmdemo.mapper.UserMapper;
import com.example.ssmdemo.md5.Md5Util;
import com.example.ssmdemo.service.LoginService;
import org.apache.poi.hpsf.IllegalVariantTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class LoginserviceImpl  implements LoginService {
    @Autowired
   private UserMapper userMapper;
    @Autowired
    private LogMapper logMapper;
    @Transactional
   public User login(String email, String password, HttpServletRequest request, HttpServletResponse response){
        String md5pass = Md5Util.md5(password);
        if (userMapper.selectByEmailAndPassword(email, md5pass) != null) {
            Log log = new Log();
          User user =  userMapper.selectByEmailAndPassword(email,md5pass);
            log.setUser(user);
            log.setLastLoginTime(LocalDateTime.now());
            log.setLoginCount(0);
            logMapper.insert(log);
            user.setLastLoginTime(LocalDateTime.now());
            userMapper.updateUser(user);
            HttpSession session = request.getSession();
            System.out.println(session.getId());
            session.setAttribute("password",password);
            session.setAttribute("email", email);
            //重新设置session的超时时间
            session.setMaxInactiveInterval(60);
            System.out.println(session.getLastAccessedTime());
            System.out.println(session.getMaxInactiveInterval());
            System.out.println("用户登录成功");
            return userMapper.selectByEmailAndPassword(email, md5pass);
        }else
            throw new RuntimeException("用户登录异常");
    }

    @Override
    public List<User> selectAll() {
       return userMapper.select();
    }
}

package com.example.ssmdemo.service.impl;

import com.example.ssmdemo.config.SettingParam;
import com.example.ssmdemo.domain.Log;
import com.example.ssmdemo.domain.User;
import com.example.ssmdemo.mapper.LogMapper;
import com.example.ssmdemo.mapper.UserMapper;
import com.example.ssmdemo.md5.Md5Util;
import com.example.ssmdemo.service.LoginService;
import org.apache.poi.hpsf.IllegalVariantTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class LoginserviceImpl  implements LoginService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
   private SettingParam settingParam;

    @Value("${com.google.name}")
   private String name;

    @Autowired
   private UserMapper userMapper;

    @Autowired
    private LogMapper logMapper;

/**
 *
 * @author ZMH
 * @date 2022/6/14 11:13
 * @param email
 * @param password
 * @param request
 * @param response
 * @return User
 */

   public User login(String email, String password, HttpServletRequest request, HttpServletResponse response){

       logger.info(name);
       logger.info(settingParam.getTilte());
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
            logger.info("账号存在登录成功");
            return userMapper.selectByEmailAndPassword(email, Md5Util.md5(password));
        }else{
            User user = new User();
            user.setuId(111111);
            user.setEmail(email);
            user.setPassword(Md5Util.md5(password));
            userMapper.insert(user);
            logger.info("创建成功账户");
            return userMapper.selectByEmailAndPassword(email, Md5Util.md5(password));
        }
    }

    @Override
    public List<User> selectAll() {
       return userMapper.select();
    }
}

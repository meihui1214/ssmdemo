package com.example.ssmdemo;

import com.example.ssmdemo.domain.User;
import com.example.ssmdemo.mapper.UserMapper;
import com.example.ssmdemo.service.LoginService;
import com.example.ssmdemo.service.impl.LoginserviceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SsmDemoApplicationTests {
    @Autowired
    private LoginService loginService;
    @Autowired
    UserMapper userMapper;

    @Test
    void contextzdLoads() {
        userMapper.SelectByEmailAndPassword("zhangmeihui", "123456789");
    }

    @Test
    void dynamicSql() {
        User user = new User();
        userMapper.selectAllByEmailOrPasswordAndPassword(user).forEach(System.out::println);
    }

    @Test
    void foreachMapSql() {
        Map<String, String> stringPassMap = new HashMap<>();
        stringPassMap.put("password1", "2");
        stringPassMap.put("password8", "123456789");
        stringPassMap.put("password2", "123456789");
        stringPassMap.put("password3", "123456789");
        stringPassMap.put("password4", "123456789");
        stringPassMap.put("password5", "123456789");
        stringPassMap.put("password6", "123456789");
        stringPassMap.put("password7", "123456789");

        Map<String, String> stringEmailMap = new HashMap<>();
        stringEmailMap.put("email","zhangmeihui");
        stringEmailMap.put("email1","yughjkbh");

        userMapper.selectByEmailAndPassword(stringEmailMap, stringPassMap).forEach(System.out::println);
    }
}

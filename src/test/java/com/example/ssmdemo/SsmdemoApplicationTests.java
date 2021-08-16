package com.example.ssmdemo;

import com.example.ssmdemo.domain.User;
import com.example.ssmdemo.mapper.UserMapper;
import com.example.ssmdemo.service.LoginService;
import com.example.ssmdemo.service.impl.LoginserviceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.w3c.dom.ls.LSOutput;

@SpringBootTest
class SsmDemoApplicationTests {
    @Autowired
    private LoginService loginService;
    @Autowired
    UserMapper userMapper;

    @Test
    void contextzdLoads() {
        userMapper.SelectByEmailAndPassword("zhangmeihui","123456789");
    }
}

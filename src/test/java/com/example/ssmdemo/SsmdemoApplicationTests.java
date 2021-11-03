package com.example.ssmdemo;

import com.example.ssmdemo.domain.Log;
import com.example.ssmdemo.domain.User;
import com.example.ssmdemo.mapper.LogMapper;
import com.example.ssmdemo.mapper.UserMapper;
import com.example.ssmdemo.service.LoginService;
import com.example.ssmdemo.service.impl.LoginserviceImpl;
import io.micrometer.core.instrument.util.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.w3c.dom.ls.LSOutput;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class SsmDemoApplicationTests {
    @Autowired
    private LoginService loginService;
    @Autowired
   private UserMapper userMapper;

    @Autowired
   private LogMapper logMapper;

    @Test
    void contextzdLoads() {
        userMapper.selectByEmailAndPassword("zhangmeihui", "123456789");
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

        Map<String, String> stringEmailMap = new HashMap<>();
        stringEmailMap.put("email","zhangmeihui");
        stringEmailMap.put("email1","yughjkbh");

        userMapper.selectByEmailAndPassword(stringEmailMap, stringPassMap).forEach(System.out::println);
    }

    @Test
    void logLeftUserSql(){
        logMapper.selectAllLogs(1).forEach(System.out::println);
    }
    @Test
    void insertLog(){
        Log log =new Log();
        log.setLogId(1);
        User user = new User();
        user.setuId(1);
        log.setUser(user);
        log.setLoginCount(1);
        log.setLastLoginTime(LocalDateTime.now());
        logMapper.insert(log);
    }
    @Test
     void annotationTest(){
        List<Map<String, Object>> maps = userMapper.selectAll();


        maps = maps.stream().sorted((o1, o2) -> {
            if (StringUtils.isEmpty(o1.get("EditTime").toString()) || StringUtils.isEmpty(o2.get("EditTime").toString())) {
                return o1.get("DictCode").toString().compareTo(o2.get("DictCode").toString());
            } else if (o1.get("EditTime").equals(o2.get("EditTime"))) {
                return o1.get("DictCode").toString().compareTo(o2.get("DictCode").toString());
            } else if (o1.get("EditTime").toString().compareTo(o2.get("EditTime").toString()) > 0)
                return -1;
            return 1;
            /*if (StringUtils.isEmpty(o1.get("EditTime").toString())||StringUtils.isEmpty(o2.get("EditTime").toString())
            || o1.get("EditTime").equals(o2.get("EditTime"))) {
                return o1.get("dictcode").toString().compareTo(o2.get("dictcode").toString());
            }
            else {
               if (o1.get("EditTime").toString().compareTo(o2.get("EditTime").toString()) >0)
                   return -1;
               return 1;
            }*/
        }).collect(Collectors.toList());
        maps.forEach(System.out::println);
    }
}

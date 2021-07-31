package com.example.ssmdemo.Controller;

import com.example.ssmdemo.domain.User;
import com.example.ssmdemo.service.LoginService;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.util.Map;

@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping("login")
    @ResponseBody
    public void login(@RequestBody Map<String,Object> params){
        if (StringUtils.isNotEmpty(String.valueOf(params.get("id"))) && StringUtils.isNotEmpty(String.valueOf(params.get("password"))){
            loginService.login((Integer) params.get("id"),(String) params.get("password"));
            if (loginService.login((Integer)params.get("id"),(String)params.get("password")) != null){

            }
        }
    }
}

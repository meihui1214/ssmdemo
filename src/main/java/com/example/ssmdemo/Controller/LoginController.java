package com.example.ssmdemo.Controller;

import com.example.ssmdemo.domain.User;
import com.example.ssmdemo.service.LoginService;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> login(@RequestBody Map<String, Object> params) {
        Map<String,Object>  map = new HashMap<>();
        if (StringUtils.isNotEmpty(String.valueOf(params.get("id"))) && StringUtils.isNotEmpty(String.valueOf(params.get("password")))) {
            if (loginService.login((Integer) params.get("id"), (String) params.get("password")) != null) {
               map.put("login",loginService.selectAll());
            }
        }
        return map;
    }
}

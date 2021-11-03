package com.example.ssmdemo.Controller;

import com.example.ssmdemo.domain.User;
import com.example.ssmdemo.service.LoginService;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/userLogin")
public class LoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(request.getParameter("email"));
        try {
            if (loginService.login(params.get("email").toString(), params.get("password").toString(), request, response) != null) {
                map.put("login", loginService.selectAll());
            }
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }
        System.out.println(request.getSession(false).getAttribute("email"));
        System.out.println(request.getSession(false).getAttribute("password"));
        Object email = request.getSession().getAttribute("email");
        redirectAttributes.addAttribute("email", email);
        model.addAttribute("email1",email);
        return map;
    }

    @RequestMapping("/login1")
    @ResponseBody
    public Map<String, Object> login1(@RequestParam("1") String email,@RequestParam("2") String password, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(request.getAttribute("1"));
        System.out.println(request.getSession().getAttribute("1"));
        try {
            if (loginService.login(email,password ,request, response) != null) {
                map.put("login", loginService.selectAll());
            }
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }
        Object email1 = request.getSession().getAttribute("1");
        redirectAttributes.addAttribute("email", email1);
        model.addAttribute("email1",email1);
        return map;
    }

    @RequestMapping("/select")
    @ResponseBody
    public String select(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("email") String email,@ModelAttribute("email1")String email1) {
        HttpSession session = request.getSession(false);
        System.out.println(email);
        System.out.println(email1);
        String email2 = null;
        if (session != null) {
            email2 = (String) session.getAttribute("email");
            return email1;
        }
        return "null";
    }
}

package com.example.ssmdemo.interceptor;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SignatureException;
import java.util.Date;

public class TokenInterceptor implements HandlerInterceptor {

    /*public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws SignatureException, Exception {
        *//* 地址过滤
        String uri = request.getRequestURI() ;
        if (uri.contains("/login")){
            return true ;
        } *//*
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //当请求为方法时，再转换类型
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        // 获取控制器上是否存在忽略检查注解
        IgnoreSecurity declaredAnnotation = handlerMethod.getBean().getClass().getDeclaredAnnotation(IgnoreSecurity.class);
        // 获取方法上是否存在忽略检查的注解
        IgnoreSecurity declaredAnnotation1 = handlerMethod.getMethod().getDeclaredAnnotation(IgnoreSecurity.class);
        if (declaredAnnotation != null || declaredAnnotation1 != null) {
            // 配置了忽略检查的自定义注解直接返回
            return true;
        }
        *//* Token 验证 *//*
        String token = request.getHeader("X-Token");
        if(StringUtils.isEmpty(token)){
            token = request.getParameter("X-Token");
        }
        if(StringUtils.isEmpty(token)){
            throw new TokenException("X-Token" + "不能为空");
        }

        Claims claims = null;
        *//*
         * 拿到token校验是否过期，如果过期则直接抛出异常
         * 反之校验token失效时间，如果距离当前时间还剩一分钟，则重置token
         * *//*
        try{
            claims = JwtConfig.getTokenClaim(token);
            if (claims == null || JwtConfig.isTokenExpired(claims.getExpiration())) {
                //response.sendError(404, "");
                throw new TokenException("X-Token" + "失效，请重新登录。");
            } else {
                Date expiration = claims.getExpiration();
                Date d = new Date();
                // 获取token失效时间与当前时间的差值，如果小于一分钟则给前端重新返回令牌
                long d_value = expiration.getTime() - d.getTime();
                String userID = JwtConfig.getUsernameFromToken(token);
                if (d_value <= 1000*60*10) {
                    String newToken = JwtConfig.createToken(userID);
                    //访问控制公开标头，防止前端看不到
                    response.setHeader("Access-Control-Expose-Headers", "access_token");
                    response.setHeader("access_token", newToken);
                }
            }
        }catch (Exception e){
            throw new TokenException("X-Token" + "失效，请重新登录。");
        }

        *//* 设置 identityId 用户身份ID *//*
        request.setAttribute("identityId", claims.getSubject());
        return true;
    }*/

}

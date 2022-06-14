package com.example.ssmdemo.config;

import com.example.ssmdemo.interceptor.MyInterceptor;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//将拦截器配置到mvc中
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor())
                .addPathPatterns("/**") //拦截的路径
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/swagger-ui.html/**");
        super.addInterceptors(registry);
    }
}

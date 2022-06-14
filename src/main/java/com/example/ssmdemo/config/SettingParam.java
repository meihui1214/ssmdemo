package com.example.ssmdemo.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;



@Component
@ConfigurationProperties(prefix = "com.google")//此注解就是为了实现将配置文件中的参数转换为类
//@PropertySource("classpath:application-prod.properties")//读取配置文件资源
@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 自定义application变量(当配置文件增加自定义变量，此类也需增加) 可以用 @Value("${com.google.name}") 即可，也可以 注入
 * @author ZMH
 * @date 2022/6/14 11:14
 */
public class SettingParam {
    private String name;
    private String tilte;
}

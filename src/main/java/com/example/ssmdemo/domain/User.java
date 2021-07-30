package com.example.ssmdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Integer uId;
    private String email;
    private String password;
    private Integer loginCount;
    private LocalDateTime lastLoginTime;

}

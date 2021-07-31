package com.example.ssmdemo.mapper;

import com.example.ssmdemo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> select();
    int insert(User user);
    User SelectByUIdAndPassword(@Param("uId")Integer uId,@Param("password") String password);
    User selectUIdByEmail(@Param("email") String email);
    int deleteByUId(@Param("uId") Integer uId);
}

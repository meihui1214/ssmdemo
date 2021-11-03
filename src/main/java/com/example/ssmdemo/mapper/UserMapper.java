package com.example.ssmdemo.mapper;

import com.example.ssmdemo.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {



    List<User> selectByEmailAndPassword(@Param("emails") Map<String, String> emails, @Param("params") Map<String, String> params);

    List<User> selectAllByEmailOrPasswordAndPassword(User user);

    List<User> select();

    int updateUser(User user);

    int insert(User user);

    User selectByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    User selectUIdByEmail(@Param("email") String email);

    int deleteByUId(@Param("uId") Integer uId);

    List<Map<String,Object>> selectAll();
}

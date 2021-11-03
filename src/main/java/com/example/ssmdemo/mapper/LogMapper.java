package com.example.ssmdemo.mapper;

import com.example.ssmdemo.domain.Log;
import com.example.ssmdemo.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface LogMapper {
    List<Log> selectAllLogs(@Param("logId") Integer logId);

    int insert(Log log);
}

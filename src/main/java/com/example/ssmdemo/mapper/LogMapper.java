package com.example.ssmdemo.mapper;

import com.example.ssmdemo.domain.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogMapper {
    List<Log> selectAllLogs(@Param("logId")Integer logId);
    int insert (Log log);
}

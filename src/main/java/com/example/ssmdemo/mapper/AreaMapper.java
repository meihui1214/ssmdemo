package com.example.ssmdemo.mapper;

import com.example.ssmdemo.domain.Area;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface AreaMapper {
    int insert (Map<String,String> map);

    int updataAreaByAreaCode (Area area);
}

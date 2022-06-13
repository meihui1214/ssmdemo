package com.example.ssmdemo;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.ssmdemo.domain.Area;
import com.example.ssmdemo.domain.Log;
import com.example.ssmdemo.domain.User;
import com.example.ssmdemo.mapper.AreaMapper;
import com.example.ssmdemo.mapper.LogMapper;
import com.example.ssmdemo.mapper.UserMapper;
import com.example.ssmdemo.service.LoginService;
import com.example.ssmdemo.service.impl.LoginserviceImpl;
import io.micrometer.core.instrument.util.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.EndpointConverter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.w3c.dom.ls.LSOutput;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class SsmDemoApplicationTests {
    @Autowired
    private LoginService loginService;
    @Autowired
   private UserMapper userMapper;
    @Autowired
    private AreaMapper areaMapper;
    @Autowired
   private LogMapper logMapper;

    @Test
    void contextzdLoads() {
        userMapper.selectByEmailAndPassword("zhangmeihui", "123456789");
    }

    @Test
    void dynamicSql() {
        User user = new User();
        userMapper.selectAllByEmailOrPasswordAndPassword(user).forEach(System.out::println);
    }

    @Test
    void foreachMapSql() {
        Map<String, String> stringPassMap = new HashMap<>();
        stringPassMap.put("password1", "2");
        stringPassMap.put("password8", "123456789");

        Map<String, String> stringEmailMap = new HashMap<>();
        stringEmailMap.put("email","zhangmeihui");
        stringEmailMap.put("email1","yughjkbh");

        userMapper.selectByEmailAndPassword(stringEmailMap, stringPassMap).forEach(System.out::println);
    }

    @Test
    void logLeftUserSql(){
        logMapper.selectAllLogs(1).forEach(System.out::println);
    }
    @Test
    void insertLog(){
        Log log =new Log();
        log.setLogId(1);
        User user = new User();
        user.setuId(1);
        log.setUser(user);
        log.setLoginCount(1);
        log.setLastLoginTime(LocalDateTime.now());
        logMapper.insert(log);
    }
    @Test
     void annotationTest(){
        List<Map<String, Object>> maps = userMapper.selectAll();


        maps = maps.stream().sorted((o1, o2) -> {
            if (StringUtils.isEmpty(o1.get("EditTime").toString()) || StringUtils.isEmpty(o2.get("EditTime").toString())) {
                return o1.get("DictCode").toString().compareTo(o2.get("DictCode").toString());
            } else if (o1.get("EditTime").equals(o2.get("EditTime"))) {
                return o1.get("DictCode").toString().compareTo(o2.get("DictCode").toString());
            } else if (o1.get("EditTime").toString().compareTo(o2.get("EditTime").toString()) > 0)
                return -1;
            return 1;
            /*if (StringUtils.isEmpty(o1.get("EditTime").toString())||StringUtils.isEmpty(o2.get("EditTime").toString())
            || o1.get("EditTime").equals(o2.get("EditTime"))) {
                return o1.get("dictcode").toString().compareTo(o2.get("dictcode").toString());
            }
            else {
               if (o1.get("EditTime").toString().compareTo(o2.get("EditTime").toString()) >0)
                   return -1;
               return 1;
            }*/
        }).collect(Collectors.toList());
        maps.forEach(System.out::println);
    }
    @Test
    void test1(){
        String s ="{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{\"adcode\":331002,\"name\":\"椒江区\",\"center\":[121.431049,28.67615],\"centroid\":[121.452074,28.660696],\"childrenNum\":0,\"level\":\"district\",\"parent\":{\"adcode\":331000},\"subFeatureIndex\":0,\"acroutes\":[100000,330000,331000]},\"geometry\":{\"type\":\"MultiPolygon\"}},{\"type\":\"Feature\",\"properties\":{\"adcode\":331003,\"name\":\"黄岩区\",\"center\":[121.262138,28.64488],\"centroid\":[121.081113,28.598872],\"childrenNum\":0,\"level\":\"district\",\"parent\":{\"adcode\":331000},\"subFeatureIndex\":1,\"acroutes\":[100000,330000,331000]},\"geometry\":{\"type\":\"MultiPolygon\"}},{\"type\":\"Feature\",\"properties\":{\"adcode\":331004,\"name\":\"路桥区\",\"center\":[121.37292,28.581799],\"centroid\":[121.481896,28.540446],\"childrenNum\":0,\"level\":\"district\",\"parent\":{\"adcode\":331000},\"subFeatureIndex\":2,\"acroutes\":[100000,330000,331000]},\"geometry\":{\"type\":\"MultiPolygon\"}},{\"type\":\"Feature\",\"properties\":{\"adcode\":331022,\"name\":\"三门县\",\"center\":[121.376429,29.118955],\"centroid\":[121.510948,29.011446],\"childrenNum\":0,\"level\":\"district\",\"parent\":{\"adcode\":331000},\"subFeatureIndex\":3,\"acroutes\":[100000,330000,331000]},\"geometry\":{\"type\":\"MultiPolygon\"}},{\"type\":\"Feature\",\"properties\":{\"adcode\":331023,\"name\":\"天台县\",\"center\":[121.031227,29.141126],\"centroid\":[120.977207,29.145258],\"childrenNum\":0,\"level\":\"district\",\"parent\":{\"adcode\":331000},\"subFeatureIndex\":4,\"acroutes\":[100000,330000,331000]},\"geometry\":{\"type\":\"MultiPolygon\"}},{\"type\":\"Feature\",\"properties\":{\"adcode\":331024,\"name\":\"仙居县\",\"center\":[120.735074,28.849213],\"centroid\":[120.634355,28.73327],\"childrenNum\":0,\"level\":\"district\",\"parent\":{\"adcode\":331000},\"subFeatureIndex\":5,\"acroutes\":[100000,330000,331000]},\"geometry\":{\"type\":\"MultiPolygon\"}},{\"type\":\"Feature\",\"properties\":{\"adcode\":331081,\"name\":\"温岭市\",\"center\":[121.373611,28.368781],\"centroid\":[121.427094,28.38848],\"childrenNum\":0,\"level\":\"district\",\"parent\":{\"adcode\":331000},\"subFeatureIndex\":6,\"acroutes\":[100000,330000,331000]},\"geometry\":{\"type\":\"MultiPolygon\"}},{\"type\":\"Feature\",\"properties\":{\"adcode\":331082,\"name\":\"临海市\",\"center\":[121.131229,28.845441],\"centroid\":[121.228824,28.847925],\"childrenNum\":0,\"level\":\"district\",\"parent\":{\"adcode\":331000},\"subFeatureIndex\":7,\"acroutes\":[100000,330000,331000]},\"geometry\":{\"type\":\"MultiPolygon\"}},{\"type\":\"Feature\",\"properties\":{\"adcode\":331083,\"name\":\"玉环市\",\"center\":[121.232337,28.12842],\"centroid\":[121.257196,28.169948],\"childrenNum\":0,\"level\":\"district\",\"parent\":{\"adcode\":331000},\"subFeatureIndex\":8,\"acroutes\":[100000,330000,331000]},\"geometry\":{\"type\":\"MultiPolygon\"}}]}";
        JSONObject jsonObject = JSON.parseObject(s);
        JSONArray features = jsonObject.getJSONArray("features");
        for (Object feature : features) {
            feature = (JSONObject)feature;
            JSONObject properties = ((JSONObject) feature).getJSONObject("properties");
            String name = properties.getString("name");
            JSONArray center = properties.getJSONArray("center");
            String longitude = center.get(0).toString();
            String latitude =  center.get(1).toString();
            Integer areaCode = properties.getInteger("adcode");

            Area area = new Area();
            area.setAreaName(name);
            area.setAreaCode(String.valueOf(areaCode));
            area.setID(String.valueOf(areaCode));
            area.setLatitude(latitude);
            area.setLongitude(longitude);
            areaMapper.updataAreaByAreaCode(area);


            System.out.println(name +" "+longitude+" "+latitude+" "+areaCode);

        }
        System.out.println();

    }
}

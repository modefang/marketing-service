package com.yun.demo.springbootdemo.controller;

import com.yun.demo.springbootdemo.annotation.LimitRequest;
import com.yun.demo.springbootdemo.constant.ResponseEnum;
import com.yun.demo.springbootdemo.pojo.ResponsePojo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @LimitRequest(times = 10)
    @GetMapping("/hello")
    public ResponsePojo hello() {
        String sql = "select count(*) from count";
        String databaseCount =  jdbcTemplate.queryForObject(sql, String.class);

        stringRedisTemplate.opsForValue().setIfAbsent("count","1");
        String redisCount = stringRedisTemplate.opsForValue().get("count");

        return new ResponsePojo(ResponseEnum.SUCCESS, countToMap(databaseCount, redisCount));
    }

    @GetMapping("/count/add")
    public ResponsePojo addCount() {
        String sql = "insert into count (`time`) value (now())";
        if(jdbcTemplate.update(sql) < 1) {
            return new ResponsePojo(ResponseEnum.ERROR_INSERT);
        }
        String countSql = "select count(*) from count";
        String databaseCount =  jdbcTemplate.queryForObject(countSql, String.class);

        stringRedisTemplate.opsForValue().setIfAbsent("count","1");
        Integer count = Integer.valueOf(stringRedisTemplate.opsForValue().get("count"));
        String redisCount = (++count).toString();
        stringRedisTemplate.opsForValue().set("count", redisCount);

        return new ResponsePojo(ResponseEnum.SUCCESS, countToMap(databaseCount, redisCount));
    }

    private Map<String, Object> countToMap(String databaseCount, String redisCount) {
        Map<String, Object> data = new HashMap<>();
        data.put("databaseCount", databaseCount);
        data.put("redisCount", redisCount);
        return data;
    }

    private String data(String databaseCount, String redisCount) {
        String data = "{\"content\": \"hello, spring boot.<br>database count: <strong>#databaseCount#</strong><br>redis count: <strong>#redisCount#</strong>\"}";

        return data.replace("#databaseCount#", databaseCount)
                .replace("#redisCount#", redisCount);
    }

}

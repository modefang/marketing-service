package com.yun.demo.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/hello")
    public String hello() {
        String sql = "select count(*) from count";
        String databaseCount =  jdbcTemplate.queryForObject(sql, String.class);

        stringRedisTemplate.opsForValue().setIfAbsent("count:","1");
        String redisCount = stringRedisTemplate.opsForValue().get("count");
        if(redisCount == null) {
            return result("201", "redis value is null!", "-1");
        }

        return result("101", "ok", data(databaseCount, redisCount));
    }

    @GetMapping("/count/add")
    public String addCount() {
        String sql = "insert into count (`time`) value (now())";
        if(jdbcTemplate.update(sql) < 1) {
            return result("201", "insert into database error!", "-1");
        }
        String countSql = "select count(*) from count";
        String databaseCount =  jdbcTemplate.queryForObject(countSql, String.class);

        stringRedisTemplate.opsForValue().setIfAbsent("count:","1");
        Integer count = Integer.valueOf(stringRedisTemplate.opsForValue().get("count"));
        String redisCount = (++count).toString();
        stringRedisTemplate.opsForValue().set("count", redisCount);

        return result("101", "ok", data(databaseCount, redisCount));
    }

    private String result(String code, String message, String data) {
        String result = "{\"code\": \"#code#\", \"message\": \"#message#\", \"data\": \"#data#\"}";

        return result.replace("#code#", code)
                    .replace("#message#", message)
                    .replace("#data#", data);
    }

    private String data(String databaseCount, String redisCount) {
        String data = "{\"content\": \"hello, spring boot.<br>database count: <strong>#databaseCount#</strong><br>redis count: <strong>#redisCount#</strong>\"}";

        return data.replace("#databaseCount#", databaseCount)
                .replace("#redisCount#", redisCount);
    }

}

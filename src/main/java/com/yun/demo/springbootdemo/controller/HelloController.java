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

    String resultTemplate = "{\"hello\": \"hello, spring boot.<br>database count: <strong>#databaseCount#</strong><br>redis count: <strong>#redisCount#</strong>\"}";

    @GetMapping("/hello")
    public String hello() {
        String sql = "select count(*) from count";
        String databaseCount =  jdbcTemplate.queryForObject(sql, String.class);

        String redisCount = stringRedisTemplate.opsForValue().get("count");
        if(redisCount == null) {
            stringRedisTemplate.opsForValue().set("count", "1");
            redisCount = "1";
        }

        return this.resultTemplate.replace("#databaseCount#", databaseCount)
                                .replace("#redisCount#", redisCount);
    }

    @GetMapping("/count/add")
    public String addCount() {
        String result = "{\"code\": \"#code#\", \"message\": \"#message#\", \"data\": \"#data#\"}";

        String sql = "insert into count (`time`) value (now())";
        if(jdbcTemplate.update(sql) < 1) {
            return result.replace("#code#", "201")
                    .replace("#message#", "insert into db error!")
                    .replace("#data#", "null");
        }
        String countSql = "select count(*) from count";
        String databaseCount =  jdbcTemplate.queryForObject(countSql, String.class);

        stringRedisTemplate.opsForValue().setIfAbsent("count:","1");
        Integer redisCount = Integer.valueOf(stringRedisTemplate.opsForValue().get("count"));
        stringRedisTemplate.opsForValue().set("count", (++redisCount).toString());

        String data = this.resultTemplate.replace("#databaseCount#", databaseCount)
                                        .replace("#redisCount#", redisCount.toString());
        return result.replace("#code#", "101")
                .replace("#message#", "ok")
                .replace("#data#", data);
    }

}

package com.snailmann.tensquare.user.controller;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    RedisTemplate redisTemplate;

    public void init() {
        redisTemplate.opsForValue().set("test", "test");
        String test = (String) redisTemplate.opsForValue().get("test");
        System.out.println(test);
    }

}
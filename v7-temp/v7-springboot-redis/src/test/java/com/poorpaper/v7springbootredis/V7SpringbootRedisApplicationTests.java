package com.poorpaper.v7springbootredis;

import jdk.nashorn.internal.ir.annotations.Reference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

@SpringBootTest
class V7SpringbootRedisApplicationTests {

    @Resource(name = "redisTemplate1")
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        Object k1 = redisTemplate.opsForValue().get("k1");
        System.out.println(k1);
    }

}

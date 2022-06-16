package com.poorpaper.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:redis.xml")
public class SpringDataRedisTest {

    // IOC
    // controller service mapper
    // spring整合第三方开发api

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void stringTest() {
        redisTemplate.opsForValue().set("smallTarget一二三", "亿个1");
        Object smallTarget = redisTemplate.opsForValue().get("smallTarget一二三");
        System.out.println(smallTarget);
    }

    @Test
    public void hashTest() {
        redisTemplate.opsForHash().put("book", "name", "FNMDP");
        Object o = redisTemplate.opsForHash().get("book", "name");
        System.out.println(o);
    }

    @Test
    public void runTest() {
        long start = System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++)
//            redisTemplate.opsForValue().set("k" + i, "v" + i);
        redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                for (int i = 0; i < 10000; i++)
                    redisTemplate.opsForValue().set("k" + i, "v" + i);
                return null;
            }
        });

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @Test
    public void ttlTest() {
        redisTemplate.opsForValue().set("18918988991", "777");
        redisTemplate.expire("18918988991", 2L, TimeUnit.MINUTES);
        Long expire = redisTemplate.getExpire("18918988991");
        System.out.println(expire);
    }
}

package com.poorpaper.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JedisTest {

    @Test
    public void stringTest() {
        Jedis jedis = new Jedis("192.168.56.129", 6379);
        jedis.auth("java1907");
        jedis.set("target", "poorpaper");
        String target = jedis.get("target");
        System.out.println(target);

        for (int i = 0; i < 10; i++)
            jedis.incr("count");
        String count = jedis.get("count");
        System.out.println(count);
    }

    @Test
    public void otherTest() {
        Jedis jedis = new Jedis("192.168.56.129", 6379);
        jedis.auth("java1907");
        jedis.lpush("list", "a", "b");
        jedis.rpush("list", "b", "a");
        List<String> list = jedis.lrange("list", 0, -1);

        Long sadd = jedis.sadd("set", "a", "b", "b");
        System.out.println(sadd);

        Map<String, Double> map = new HashMap<>();
        map.put("java", 100.0);
        map.put("sox", 1000.0);
        Long hotbook = jedis.zadd("hotbook", map);
        System.out.println(jedis.zrange("hotbook", 0, -1));
    }
}

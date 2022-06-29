package com.poorpaper.v7cartservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.poorpaper.api.ICartService;
import com.poorpaper.commons.pojo.ResultBean;
import com.poorpaper.pojo.CartItem;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CartServiceImpl implements ICartService {

    @Resource(name = "myStringRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResultBean add(String token, Long productId, Integer count) {
        // 1.根据token查询到购物车信息
        StringBuilder key = new StringBuilder("user:cart:").append(token);
        Map<Object, Object> cart = redisTemplate.opsForHash().entries(key.toString());
        // 2.购物车已存在，且存在该商品，就修改商品数量
        if (cart != null) {
            if (redisTemplate.opsForHash().hasKey(key.toString(), productId)) {
                CartItem cartItem = (CartItem) redisTemplate.opsForHash().get(key.toString(), productId);
                cartItem.setCount(cartItem.getCount() + count);
                cartItem.setUpdateTime(new Date());
                redisTemplate.opsForHash().put(key.toString(), productId, cartItem);
                redisTemplate.expire(key.toString(), 15, TimeUnit.DAYS);
                return new ResultBean("200", true);
            }
        }
        // 第一次操作购物车 或者 购物车中不存在该商品
        // 就直接添加商品到购物车里
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setCount(count);
        cartItem.setUpdateTime(new Date());
        // 添加购物项到购物车中
        redisTemplate.opsForHash().put(key.toString(), productId, cartItem);
        // 设置有效期
        redisTemplate.expire(key.toString(), 15, TimeUnit.DAYS);
        return new ResultBean("200", true);
//        // 3.非第一次操作购物车
//        // 3.1需要判断当前购物车是否已存在该商品
//        // 若存在则直接更改数量
//        if (redisTemplate.opsForHash().hasKey(key.toString(), productId)) {
//            CartItem cartItem = (CartItem) redisTemplate.opsForHash().get(key.toString(), productId);
//            cartItem.setCount(cartItem.getCount() + count);
//            cartItem.setUpdateTime(new Date());
//            redisTemplate.opsForHash().put(key.toString(), productId, cartItem);
//            redisTemplate.expire(key.toString(), 15, TimeUnit.DAYS);
//            return new ResultBean("200", true);
//        }
//
//        // 购物车不存在该商品
//        CartItem cartItem = new CartItem();
//        cartItem.setProductId(productId);
//        cartItem.setCount(count);
//        cartItem.setUpdateTime(new Date());
//        redisTemplate.opsForHash().put(key.toString(), productId, cartItem);
//        redisTemplate.expire(key.toString(), 15, TimeUnit.DAYS);
//        return new ResultBean("200", true);
    }

    @Override
    public ResultBean del(String token, Long productId) {
        StringBuilder key = new StringBuilder("user:cart:").append(token);
        Long delete = redisTemplate.opsForHash().delete(key.toString(), productId);
        if (delete == 1L) {
            return new ResultBean("200", true);
        }
        return new ResultBean("404", false);
    }

    @Override
    public ResultBean update(String token, Long productId, Integer count) {
        StringBuilder key = new StringBuilder("user:cart:").append(token);
        if (redisTemplate.opsForHash().hasKey(key.toString(), productId)) {
            redisTemplate.opsForHash().put(key.toString(), productId, new CartItem(productId, count, new Date()));
            return new ResultBean("200", true);
        }
        return new ResultBean("404", false);
    }

    @Override
    public ResultBean query(String token) {
        // 1.根据token查询到购物车信息
        StringBuilder key = new StringBuilder("user:cart:").append(token);
        Map<Object, Object> cart = redisTemplate.opsForHash().entries(key.toString());
        // 2.
        if (cart != null) {
            // 1.获取到values并没有按照时间来排序
            Collection<Object> values = cart.values();
            // TreeSet时间相同会丢失
            TreeSet<CartItem> treeSet = new TreeSet<>();
            // 无序变有序
            for (Object value : values) {
                CartItem cartItem = (CartItem) value;
                treeSet.add(cartItem);
            }
            List<CartItem> result = new ArrayList<>(treeSet);
            // TODO CartItem -> CartItemVO
            return new ResultBean("200", result);
        }
        return new ResultBean("404", null);
    }

    @Override
    public ResultBean merge(String noLoginKey, String loginKey) {
        // 目标：将未登录购物车合并到已登录购物车
        // 1.判断未登录购物车是否存在
        Map<Object, Object> noLoginCart = redisTemplate.opsForHash().entries(noLoginKey);
        if (noLoginCart.size() == 0) {
            return new ResultBean("200", "无需合并");
        }

        // 2.判断已登录购物车是否存在
        Map<Object, Object> loginCart = redisTemplate.opsForHash().entries(loginKey);
        if (loginCart.size() == 0) {
            //1,直接将未登录购物车变成已登录购物车即可
            redisTemplate.opsForHash().putAll(loginKey, noLoginCart);
            //2.删除未登录的购物车
            Map<Object, Object> tmp = redisTemplate.opsForHash().entries(loginKey);
            redisTemplate.delete(noLoginKey);
            return new ResultBean("200","合并成功！");
        }

        // 3.两辆购物车都存在
        // 3.1相同的商品，则数量叠加
        // 3.2否则直接添加
        // 以已登录购物车为源头，然后遍历未登录的购物车，不断作比较
        Set<Map.Entry<Object, Object>> noLoginEntries = noLoginCart.entrySet();
        for (Map.Entry<Object, Object> noLoginEntry : noLoginEntries) {
            CartItem cartItem = (CartItem) redisTemplate.opsForHash().get(loginKey, noLoginEntry.getKey());
            if (cartItem != null) {
                // 存在则数量叠加
                CartItem noLoginCartItem = (CartItem) noLoginEntry.getValue();
                cartItem.setCount(cartItem.getCount() + noLoginCartItem.getCount());
                cartItem.setUpdateTime(new Date());
                redisTemplate.opsForHash().put(loginKey, noLoginEntry.getKey(), cartItem);
            } else {
                redisTemplate.opsForHash().put(loginKey, noLoginEntry.getKey(), noLoginEntry.getValue());
            }
        }
        redisTemplate.delete(noLoginKey);

        return new ResultBean("200", "合并成功");
    }
}

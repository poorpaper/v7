package com.poorpaper.v7cartservice;

import com.poorpaper.api.ICartService;
import com.poorpaper.commons.pojo.ResultBean;
import com.poorpaper.pojo.CartItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class V7CartServiceApplicationTests {

    @Autowired
    private ICartService cartService;

    @Test
    void addTest() {
        cartService.add("123", 1L, 100);
        cartService.add("123", 2L, 100);
        ResultBean resultBean = cartService.add("123", 1L, 100);
        System.out.println(resultBean.getData());
    }

    @Test
    public void queryTest() {
        ResultBean resultBean = cartService.query("123");
        List<CartItem> list = (List<CartItem>) resultBean.getData();
        System.out.println(list);
    }

    @Test
    public void delTest() {
        cartService.del("123", 2L);
    }

    @Test
    public void updateTest() {
        cartService.update("123", 6L, 1000);
    }
}

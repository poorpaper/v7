package com.poorpaper.v7sso;

import com.alibaba.dubbo.config.annotation.Reference;
import com.poorpaper.api.IUserService;
import com.poorpaper.commons.pojo.ResultBean;
import com.poorpaper.entity.TUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class V7SsoApplicationTests {

    @Reference
    private IUserService userService;

    @Test
    void checkLoginTest() {
        TUser user = new TUser();
        user.setUsername("123@12.com");
        user.setPassword("123456");
        ResultBean resultBean = userService.checkLogin(user);
        System.out.println(resultBean.getStatusCode());
    }

}

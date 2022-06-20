package com.poorpaper.v7emailservice;

import com.poorpaper.api.IEmailService;
import com.poorpaper.commons.pojo.ResultBean;
import com.poorpaper.v7emailservice.service.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class V7EmailServiceApplicationTests {

    @Autowired
    private IEmailService emailService;

    @Test
    void sendBirthday() {
        ResultBean resultBean = emailService.sendBirthday("sdfghjkzx@qq.com", "777");
        System.out.println(resultBean.getData());
    }

}

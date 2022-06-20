package com.poorpaper.v7smsservice;

import com.poorpaper.api.ISMS;
import com.poorpaper.api.pojo.SMSResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class V7SmsServiceApplicationTests {

    @Autowired
    private ISMS sms;

    @Test
    void contextLoads() {
        SMSResponse smsResponse = sms.sendCodeMessage("12345678910", "77777");
        System.out.println(smsResponse.getCode());
    }

}

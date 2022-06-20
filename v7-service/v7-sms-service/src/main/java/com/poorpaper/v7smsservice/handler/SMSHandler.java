package com.poorpaper.v7smsservice.handler;

import com.poorpaper.api.ISMS;
import com.poorpaper.commons.constant.MQConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class SMSHandler {
    // 1. 声明队列 center-product-exchange-search-queue
    // 2. 绑定交换机
    // 借助管理平台来实现
    @Autowired
    private ISMS sms;

    @RabbitHandler
    @RabbitListener(queues = MQConstant.QUEUE.SMS_EXCHANGE_CODE_QUEUE)
    public void processSendCode(Map<String, String> map) {
        String identification = map.get("identification");
        String code = map.get("code");
        log.info("identification：{}的消息", identification);
        sms.sendCodeMessage(identification, code);
    }

    @RabbitHandler
    @RabbitListener(queues = MQConstant.QUEUE.SMS_EXCHANGE_BIRTHDAY_QUEUE)
    public void processSendBirthdayGreeting(Map<String, String> map) {
        String identification = map.get("identification");
        String username = map.get("username");
        log.info("identification：{}的消息", identification);
        sms.sendBirthdayGreeting(identification, username);
    }
}

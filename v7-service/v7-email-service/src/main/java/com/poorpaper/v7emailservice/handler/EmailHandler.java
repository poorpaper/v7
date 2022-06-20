package com.poorpaper.v7emailservice.handler;

import com.poorpaper.api.IEmailService;
import com.poorpaper.commons.constant.MQConstant;
import com.poorpaper.commons.pojo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class EmailHandler {

    @Autowired
    private IEmailService emailService;

    @RabbitHandler
    @RabbitListener(queues = MQConstant.QUEUE.EMAIL_EXCHANGE_BIRTHDAY_QUEUE)
    public void processSendBirthday(Map<String, String> map) {
        String to = map.get("to");
        String username = map.get("username");
        log.info("to：{}的消息", to);
        emailService.sendBirthday(to, username);
    }
}

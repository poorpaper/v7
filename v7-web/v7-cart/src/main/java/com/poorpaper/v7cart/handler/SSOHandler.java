package com.poorpaper.v7cart.handler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.poorpaper.api.ICartService;
import com.poorpaper.commons.constant.MQConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SSOHandler {

    @Reference
    private ICartService cartService;

    @RabbitListener(queues = MQConstant.QUEUE.SSO_EXCHANGE_CART_QUEUE)
    @RabbitHandler
    public void process(Map<String, String> params) {
        String noLoginKey = params.get("noLoginKey");
        String loginKey = params.get("loginKey");

        cartService.merge("user:cart:" + noLoginKey, "user:cart:" + loginKey);
    }
}

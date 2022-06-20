package com.poorpaper.v7userservice.config;

import com.poorpaper.commons.constant.MQConstant;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置交换机和队列的关系
 */
@Configuration
public class RabbitConfig {

    //TODO 用代码的方式声明交换机及队列，和他们之间的绑定关系

    @Bean
    public TopicExchange initSMSExchange() {
        TopicExchange smsExchange = new TopicExchange(
                MQConstant.EXCHANGE.SMS_EXCHANGE, true, false);
        return smsExchange;
    }

    @Bean
    public TopicExchange initEmailExchange() {
        TopicExchange emailExchange = new TopicExchange(
                MQConstant.EXCHANGE.EMAIL_EXCHANGE, true, false);
        return emailExchange;
    }
}

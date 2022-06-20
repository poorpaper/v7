package com.poorpaper.v7smsservice.config;

import com.poorpaper.commons.constant.MQConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置交换机和队列的关系
 */
@Configuration
public class RabbitConfig {

    // 1.声明队列
    @Bean
    public Queue initSMSCodeQueue() {
        Queue queue = new Queue(
                MQConstant.QUEUE.SMS_EXCHANGE_CODE_QUEUE, true, false, false);
        return queue;
    }

    @Bean
    public Queue initSMSBirthdayQueue() {
        Queue queue = new Queue(
                MQConstant.QUEUE.SMS_EXCHANGE_BIRTHDAY_QUEUE, true, false, false);
        return queue;
    }

    // 2.绑定交换机
    @Bean
    public TopicExchange initSMSExchange() {
        TopicExchange userExchange = new TopicExchange(
                MQConstant.EXCHANGE.SMS_EXCHANGE, true, false);
        return userExchange;
    }

    @Bean
    public Binding bindSMSCodeQueue2SMSExchange(
            Queue initSMSCodeQueue, TopicExchange initSMSExchange) {
        return BindingBuilder.bind(initSMSCodeQueue).to(initSMSExchange).with("sms.code");
    }

    @Bean
    public Binding bindSMSBirthdayQueue2SMSExchange(
            Queue initSMSBirthdayQueue, TopicExchange initSMSExchange) {
        return BindingBuilder.bind(initSMSBirthdayQueue).to(initSMSExchange).with("sms.code");
    }
}

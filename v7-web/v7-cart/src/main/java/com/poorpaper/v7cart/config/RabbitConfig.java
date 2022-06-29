package com.poorpaper.v7cart.config;

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
    public Queue initSSOCartQueue() {
        Queue queue = new Queue(
                MQConstant.QUEUE.SSO_EXCHANGE_CART_QUEUE, true, false, false);
        return queue;
    }

    // 2.声明交换机
    @Bean
    public TopicExchange initSSOExchange() {
        TopicExchange ssoExchange = new TopicExchange(
                MQConstant.EXCHANGE.SSO_EXCHANGE, true, false);
        return ssoExchange;
    }

    // 3.绑定交换机
    @Bean
    public Binding bindSSOCartQueue2SSOExchange(
            Queue initSSOCartQueue, TopicExchange initSSOExchange) {
        return BindingBuilder.bind(initSSOCartQueue).to(initSSOExchange).with("user.login");
    }
}

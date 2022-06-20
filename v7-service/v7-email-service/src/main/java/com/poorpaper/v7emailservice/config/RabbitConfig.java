package com.poorpaper.v7emailservice.config;

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
    public Queue initEmailBirthdayQueue() {
        Queue queue = new Queue(
                MQConstant.QUEUE.EMAIL_EXCHANGE_BIRTHDAY_QUEUE, true, false, false);
        return queue;
    }

    @Bean
    public Queue initEmailCodeQueue() {
        Queue queue = new Queue(
                MQConstant.QUEUE.EMAIL_EXCHANGE_CODE_QUEUE, true, false, false);
        return queue;
    }

    // 2.绑定交换机
    @Bean
    public TopicExchange initEmailExchange() {
        TopicExchange userExchange = new TopicExchange(
                MQConstant.EXCHANGE.EMAIL_EXCHANGE, true, false);
        return userExchange;
    }

    @Bean
    public Binding bindEmailBirthdayQueue2EmailExchange(
            Queue initEmailBirthdayQueue, TopicExchange initEmailExchange) {
        return BindingBuilder.bind(initEmailBirthdayQueue).to(initEmailExchange).with("email.birthday");
    }

    @Bean
    public Binding bindProductSearchQueue2ProductExchange(
            Queue initEmailCodeQueue, TopicExchange initEmailExchange) {
        return BindingBuilder.bind(initEmailCodeQueue).to(initEmailExchange).with("email.code");
    }
}

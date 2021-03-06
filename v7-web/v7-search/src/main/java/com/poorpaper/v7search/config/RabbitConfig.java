package com.poorpaper.v7search.config;

import com.poorpaper.commons.constant.MQConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // 1.声明队列
    @Bean
    public Queue initProductSearchQueue() {
        Queue queue = new Queue(
                MQConstant.QUEUE.CENTER_PRODUCT_EXCHANGE_SEARCH_QUEUE, true, false, false);
        return queue;
    }

    // 2.绑定交换机
    @Bean
    public TopicExchange initProductExchange() {
        TopicExchange productExchange = new TopicExchange(
                MQConstant.EXCHANGE.CENTER_PRODUCT_EXCHANGE, true, false);
        return productExchange;
    }

    @Bean
    public Binding bindProductSearchQueue2ProductExchange(
            Queue initProductSearchQueue, TopicExchange initProductExchange) {
        return BindingBuilder.bind(initProductSearchQueue).to(initProductExchange).with("product.add");
    }
}

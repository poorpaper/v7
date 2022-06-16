package com.poorpaper.v7center.config;

import com.poorpaper.commons.constant.MQConstant;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange initProductExchange() {
        TopicExchange productExchange = new TopicExchange(
                MQConstant.EXCHANGE.CENTER_PRODUCT_EXCHANGE, true, false);
        return productExchange;
    }
}

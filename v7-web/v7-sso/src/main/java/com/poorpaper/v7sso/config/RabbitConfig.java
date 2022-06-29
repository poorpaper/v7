package com.poorpaper.v7sso.config;

import com.poorpaper.commons.constant.MQConstant;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置交换机和队列的关系
 */
@Configuration
public class RabbitConfig {
    @Bean
    public TopicExchange initSSOExchange() {
        TopicExchange ssoExchange = new TopicExchange(
                MQConstant.EXCHANGE.SSO_EXCHANGE, true, false);
        return ssoExchange;
    }
}

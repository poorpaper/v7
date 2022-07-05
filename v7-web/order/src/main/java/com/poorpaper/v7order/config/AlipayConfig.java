package com.poorpaper.v7order.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlipayConfig {

    @Autowired
    private AlipayProperties alipayProperties;

    @Bean
    public AlipayClient getAlipayClient() {
        AlipayClient alipayClient = new DefaultAlipayClient(
                alipayProperties.getServerUrl(),
                alipayProperties.getAppid(),
                alipayProperties.getPrivateKey(),
                alipayProperties.getFormat(),
                alipayProperties.getCharset(),
                alipayProperties.getAlipayPublicKey(),
                alipayProperties.getSignType()
        );
        return alipayClient;
    }
}

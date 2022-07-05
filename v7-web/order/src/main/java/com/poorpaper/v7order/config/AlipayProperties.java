package com.poorpaper.v7order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "alipay")
@Data
public class AlipayProperties {

    private String serverUrl;
    private String appid;
    private String privateKey;
    private String format;
    private String charset;
    private String alipayPublicKey;
    private String signType;
}

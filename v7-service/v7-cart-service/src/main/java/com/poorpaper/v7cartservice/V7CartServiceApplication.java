package com.poorpaper.v7cartservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDubbo
@MapperScan("com.poorpaper.mapper")
public class V7CartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(V7CartServiceApplication.class, args);
    }

}

package com.poorpaper.v7userservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.poorpaper.mapper")
public class V7UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(V7UserServiceApplication.class, args);
    }

}

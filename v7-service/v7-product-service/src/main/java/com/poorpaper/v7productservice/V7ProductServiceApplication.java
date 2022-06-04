package com.poorpaper.v7productservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.poorpaper.mapper")
public class V7ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(V7ProductServiceApplication.class, args);
    }

}

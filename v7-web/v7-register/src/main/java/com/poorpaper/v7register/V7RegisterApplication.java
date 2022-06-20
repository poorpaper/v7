package com.poorpaper.v7register;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDubbo
public class V7RegisterApplication {

    public static void main(String[] args) { SpringApplication.run(V7RegisterApplication.class, args);}

}

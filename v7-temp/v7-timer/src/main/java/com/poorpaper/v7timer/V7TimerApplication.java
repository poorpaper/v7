package com.poorpaper.v7timer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class V7TimerApplication {

    public static void main(String[] args) {
        SpringApplication.run(V7TimerApplication.class, args);
    }

}

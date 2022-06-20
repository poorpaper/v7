package com.poorpaper.v7timer.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MySpringBootTask {

//    @Scheduled(fixedDelay = 1000)
//    @Scheduled(fixedRate = 1000)
    @Scheduled(cron = "0/5 * * * * ?")
    public void run() {
        System.out.println(new Date());
    }
}

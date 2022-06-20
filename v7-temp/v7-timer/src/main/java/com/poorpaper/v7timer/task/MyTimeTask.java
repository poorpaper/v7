package com.poorpaper.v7timer.task;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class MyTimeTask extends TimerTask {
    @Override
    public void run() {
        System.out.println(new Date());
    }

    public static void main(String[] args) {
        MyTimeTask task = new MyTimeTask();
        Timer timer = new Timer();
//        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor();
        timer.schedule(task, 1000, 1000);
        // 每月的15号的8:00，做一个小人物
        // 1月15号，2月15号，3月15号
        // 非固定时间，不建议采用这种方式
    }
}

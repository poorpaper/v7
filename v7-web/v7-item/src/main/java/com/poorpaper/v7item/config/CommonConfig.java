package com.poorpaper.v7item.config;

import com.alibaba.dubbo.common.threadpool.ThreadPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class CommonConfig {

    @Bean
    public ThreadPoolExecutor initThreadPoolExecutor() {
        int corePoolSize = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, corePoolSize * 2,
                1L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100)
        );
        return pool;
    }
}

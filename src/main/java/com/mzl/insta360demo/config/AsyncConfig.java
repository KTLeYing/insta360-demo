package com.mzl.insta360demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @Description: 线程池异步执行相关配置
 * @Author: mzl
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "aopExecutor")
    public Executor aopExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.setThreadNamePrefix("Async-AopExecutor");
        executor.initialize();

        return executor;
    }

}
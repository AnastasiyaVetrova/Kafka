package com.puppet.frontendpracticeservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Настройка пула потоков для многопоточного режима
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(3);
        threadPool.setMaxPoolSize(10);
        threadPool.setQueueCapacity(500);
        threadPool.setThreadNamePrefix("Async-");
        threadPool.initialize();
        return threadPool;
    }
}

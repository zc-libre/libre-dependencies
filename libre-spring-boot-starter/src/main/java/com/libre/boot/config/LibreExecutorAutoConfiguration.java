package com.libre.boot.config;

import com.libre.boot.executor.RunnableWrapper;
import com.libre.boot.prop.LibreExecutorProperties;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zhao.cheng
 * @date 2021/3/3 10:45
 */

@EnableAsync
@EnableScheduling
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LibreExecutorProperties.class)
public class LibreExecutorAutoConfiguration extends AsyncConfigurerSupport {

    private final LibreExecutorProperties properties;

    public LibreExecutorAutoConfiguration(LibreExecutorProperties properties) {
        this.properties = properties;
    }

    @Override
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getCorePoolSize());
        executor.setMaxPoolSize(properties.getMaxPoolSize());
        executor.setQueueCapacity(properties.getQueueCapacity());
        executor.setKeepAliveSeconds(properties.getKeepAliveSeconds());
        executor.setThreadNamePrefix("async-executor-");
        // 传递线程变量
        executor.setTaskDecorator(RunnableWrapper::new);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}

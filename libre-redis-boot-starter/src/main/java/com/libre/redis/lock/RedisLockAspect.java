package com.libre.redis.lock;

import com.libre.core.spel.LibreExpressionEvaluator;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author zhao.cheng
 * @date 2021/5/20 10:33
 */
@Aspect
@RequiredArgsConstructor
public class RedisLockAspect implements ApplicationContextAware {

    /**
     * 表达式处理
     */
    private static final LibreExpressionEvaluator EVALUATOR = new LibreExpressionEvaluator();
    /**
     * redis 限流服务
     */
    private final RedisLockClient redisLockClient;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}

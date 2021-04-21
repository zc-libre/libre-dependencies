package com.libre.core.toolkit;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;


/**
 * @author zhao.cheng
 */
@SuppressWarnings("unchecked")
public class SpringUtils implements ApplicationContextAware {

    @Nullable
    private static ApplicationContext context;

    public SpringUtils() {
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
        SpringUtils.context = context;
    }

    @Nullable
    public static <T> T getBean(Class<T> clazz) {
        return context == null ? null : context.getBean(clazz);
    }

    @Nullable
    public static <T> T getBean(String beanName) {
        return context == null ? null : (T) context.getBean(beanName);
    }

    @Nullable
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return context == null ? null : context.getBean(beanName, clazz);
    }

    @Nullable
    public static <T> ObjectProvider<T> getBeanProvider(Class<T> clazz) {
        return context == null ? null : context.getBeanProvider(clazz);
    }

    @Nullable
    public static <T> ObjectProvider<T> getBeanProvider(ResolvableType resolvableType) {
        return context == null ? null : context.getBeanProvider(resolvableType);
    }

    @Nullable
    public static ApplicationContext getContext() {
        return context;
    }

    public static void publishEvent(ApplicationEvent event) {
        if (context != null) {
            context.publishEvent(event);
        }
    }

    public static <T> T getAopProxy(T invoker) {
        return (T) AopContext.currentProxy();
    }
}

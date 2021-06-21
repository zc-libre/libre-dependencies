package com.libre.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.libre.mybatis.permission.handler.DataPermissionProcessor;
import com.libre.mybatis.permission.handler.DefaultDataPermissionHandler;
import com.libre.mybatis.permission.handler.LibreDataPermissionHandler;
import com.libre.mybatis.permission.interceptor.DefaultDataPermissionInterceptor;
import com.libre.mybatis.permission.prop.DataPermissionProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhao.cheng
 * @date 2021/4/20 13:55
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@EnableConfigurationProperties(DataPermissionProperties.class)
@ConditionalOnProperty(prefix = "libre.data-permission", name = "enabled", havingValue = "true")
public class DataPermissionAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public LibreDataPermissionHandler dataPermissionHandler(DataPermissionProperties dataPermissionProperties,
                                                            DataPermissionProcessor dataPermissionProcessor) {
        return new DefaultDataPermissionHandler(dataPermissionProperties, dataPermissionProcessor);
    }

    @Bean
    @ConditionalOnMissingBean
    public DataPermissionInterceptor dataPermissionInterceptor(LibreDataPermissionHandler dataPermissionHandler) {
        return new DefaultDataPermissionInterceptor(dataPermissionHandler);
    }

}

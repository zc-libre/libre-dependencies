package com.libre.boot.config;

import com.libre.boot.prop.LibreXssProperties;
import com.libre.core.toolkit.SpringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhao.cheng
 * @date 2021/3/3 10:32
 */

@Configuration(proxyBeanMethods = false)
public class LibreBootAutoConfiguration {

    @Bean
    public SpringUtils springUtils() {
        return new SpringUtils();
    }
}

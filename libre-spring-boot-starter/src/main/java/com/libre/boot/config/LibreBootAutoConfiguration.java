package com.libre.boot.config;

import com.libre.boot.prop.XssProperties;
import com.libre.boot.xss.RequestFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.DispatcherType;

/**
 * @author zhao.cheng
 * @date 2021/3/3 10:32
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(XssProperties.class)
public class LibreBootAutoConfiguration {

    @Bean
    public FilterRegistrationBean<RequestFilter> spangFilterRegistration(XssProperties xssProperties) {
        FilterRegistrationBean<RequestFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new RequestFilter(xssProperties));
        registration.addUrlPatterns("/*");
        registration.setName("LibreRequestFilter");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        return registration;
    }
}

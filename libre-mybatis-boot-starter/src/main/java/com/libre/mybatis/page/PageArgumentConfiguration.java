package com.libre.mybatis.page;

import com.libre.mybatis.prop.LibreMyBatisProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author ZC
 * @date 2021/7/11 12:10
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class PageArgumentConfiguration implements WebMvcConfigurer {

	private final LibreMyBatisProperties properties;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new PageArgumentResolver(properties));
	}
}

package com.libre.mybatis.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.*;
import com.libre.mybatis.prop.LibreMyBatisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author zhao.cheng
 * @date 2021/4/12 11:18
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LibreMyBatisProperties.class)
public class LibreMyBatisAutoConfiguration {

	/**
	 * 分页插件, 对于单一数据库类型来说,都建议配置该值,避免每次分页都去抓取数据库类型
	 */
	@Bean
	@ConditionalOnMissingBean
	public PaginationInnerInterceptor paginationInnerInterceptor(LibreMyBatisProperties properties) {
		PaginationInnerInterceptor interceptor = new PaginationInnerInterceptor(properties.getDbType());
		interceptor.setOverflow(properties.getOverflow());
		interceptor.setMaxLimit(properties.getMaxLimit());
		return interceptor;

	}

	@Bean
	@ConditionalOnMissingBean
	public OptimisticLockerInnerInterceptor optimisticLockerInterceptor() {
		return new OptimisticLockerInnerInterceptor();
	}


	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor(ObjectProvider<List<InnerInterceptor>> listObjectProvider) {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		listObjectProvider.ifAvailable((interceptorList) -> {
			interceptorList.forEach(interceptor::addInnerInterceptor);
		});
		return interceptor;
	}

	@Bean
	public ConfigurationCustomizer configurationCustomizer() {
		return configuration -> configuration.setUseDeprecatedExecutor(false);
	}
}

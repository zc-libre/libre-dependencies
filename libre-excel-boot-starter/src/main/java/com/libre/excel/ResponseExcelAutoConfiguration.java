package com.libre.excel;


import com.libre.excel.aop.DynamicNameAspect;
import com.libre.excel.aop.ResponseExcelReturnValueHandler;
import com.libre.excel.config.ExcelConfigProperties;
import com.libre.excel.processor.NameProcessor;
import com.libre.excel.processor.NameSpelExpressionProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lengleng
 * @date 2020/3/29
 * <p>
 * 配置初始化
 */
@Import(ExcelHandlerConfiguration.class)
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ExcelConfigProperties.class)
public class ResponseExcelAutoConfiguration {

	private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;

	private final ResponseExcelReturnValueHandler responseExcelReturnValueHandler;

	/**
	 * SPEL 解析处理器
	 * @return NameProcessor excel名称解析器
	 */
	@Bean
	@ConditionalOnMissingBean
	public NameProcessor nameProcessor() {
		return new NameSpelExpressionProcessor();
	}

	/**
	 * Excel名称解析处理切面
	 * @param nameProcessor SPEL 解析处理器
	 * @return DynamicNameAspect
	 */
	@Bean
	@ConditionalOnMissingBean
	public DynamicNameAspect dynamicNameAspect(NameProcessor nameProcessor) {
		return new DynamicNameAspect(nameProcessor);
	}

	/**
	 * 追加 Excel返回值处理器 到 springmvc 中
	 */
	@PostConstruct
	public void setReturnValueHandlers() {
		List<HandlerMethodReturnValueHandler> returnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();

		List<HandlerMethodReturnValueHandler> newHandlers = new ArrayList<>();
		newHandlers.add(responseExcelReturnValueHandler);
		assert returnValueHandlers != null;
		newHandlers.addAll(returnValueHandlers);
		requestMappingHandlerAdapter.setReturnValueHandlers(newHandlers);
	}

}

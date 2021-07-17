

package com.libre.jetcache.config;

import com.alicp.jetcache.anno.support.ConfigMap;
import com.alicp.jetcache.anno.support.DefaultSpringEncoderParser;
import com.alicp.jetcache.anno.support.DefaultSpringKeyConvertorParser;
import com.alicp.jetcache.anno.support.SpringConfigProvider;
import com.alicp.jetcache.autoconfigure.JetCacheAutoConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libre.core.toolkit.JSONUtil;
import com.libre.jetcache.jackson.JacksonKeyConvertor;
import com.libre.jetcache.jackson.JacksonValueDecoder;
import com.libre.jetcache.jackson.JacksonValueEncoder;
import com.libre.jetcache.protostuff.ProtostuffKeyConvertor;
import com.libre.jetcache.protostuff.ProtostuffValueDecoder;
import com.libre.jetcache.protostuff.ProtostuffValueEncoder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * jetcache 配置
 *
 * @author L.cm
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(JetCacheAutoConfiguration.class)
public class JetCacheConfiguration implements InitializingBean {

	private ObjectMapper cacheMapper;

	@Bean("jacksonKeyConvertor")
	public JacksonKeyConvertor jacksonKeyConvertor() {
		return new JacksonKeyConvertor(JSONUtil.getInstance());
	}

	@Bean("jacksonValueDecoder")
	public JacksonValueDecoder jacksonValueDecoder() {
		return new JacksonValueDecoder(cacheMapper);
	}

	@Bean("jacksonValueEncoder")
	public JacksonValueEncoder jacksonValueEncoder() {
		return new JacksonValueEncoder(cacheMapper);
	}

	@Bean
	public ProtostuffKeyConvertor protostuffKeyConvertor() {
		return new ProtostuffKeyConvertor();
	}

	@Bean
	public ProtostuffValueEncoder protostuffValueEncoder() {
		return new ProtostuffValueEncoder();
	}

	@Bean
	public ProtostuffValueDecoder protostuffValueDecoder() {
		return new ProtostuffValueDecoder();
	}

	@Bean
	@ConditionalOnMissingBean
	public ConfigMap configMap() {
		return new ConfigMap();
	}

	@Bean
	public SpringConfigProvider springConfigProvider(ApplicationContext applicationContext) {
		DefaultSpringKeyConvertorParser convertorParser = new DefaultSpringKeyConvertorParser();
		convertorParser.setApplicationContext(applicationContext);
		SpringConfigProvider springConfigProvider = new SpringConfigProvider();
		springConfigProvider.setKeyConvertorParser(convertorParser);
		springConfigProvider.setApplicationContext(applicationContext);
		DefaultSpringEncoderParser encoderParser = new DefaultSpringEncoderParser();
		encoderParser.setApplicationContext(applicationContext);
		springConfigProvider.setEncoderParser(encoderParser);
		return springConfigProvider;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ObjectMapper mapper = JSONUtil.getInstance().copy();
		mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator());
		this.cacheMapper = mapper;
	}

}

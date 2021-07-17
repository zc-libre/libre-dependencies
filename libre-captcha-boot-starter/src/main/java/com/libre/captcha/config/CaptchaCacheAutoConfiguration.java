package com.libre.captcha.config;

import com.libre.captcha.cache.CaptchaCache;
import com.libre.captcha.cache.SpringCacheCaptchaCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author ZC
 * @date 2021/7/17 2:46
 */
@Order
@Configuration(proxyBeanMethods = false)
public class CaptchaCacheAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public CaptchaCache captchaCache(CacheManager cacheManager, CaptchaProperties properties) {
		return new SpringCacheCaptchaCache(cacheManager, properties);
	}
}

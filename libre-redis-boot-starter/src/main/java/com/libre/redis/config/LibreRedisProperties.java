package com.libre.redis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * redis 配置
 * @author zhao.cheng
 */
@Getter
@Setter
@ConfigurationProperties("libre.redis")
public class LibreRedisProperties {
	/**
	 * 序列化方式
	 */
	private SerializerType serializerType = SerializerType.JSON;

	private RateLimiter rateLimiter = new RateLimiter();

	@Getter
	@Setter
	private static class RateLimiter {

		private boolean enabled = Boolean.FALSE;
	}


	public enum SerializerType {
		/**
		 * json 序列化
		 */
		JSON,
		/**
		 * jdk 序列化
		 */
		JDK,
		/**
		 * ProtoStuff 序列化
		 */
		PROTOSTUFF,
	}
}

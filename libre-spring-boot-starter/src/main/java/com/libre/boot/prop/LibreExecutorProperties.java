package com.libre.boot.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.libre.boot.constant.BootConstant.EXECUTOR_CONFIG_PREFIX;

/**
 * 异步配置
 * @author Chill
 */
@Getter
@Setter
@ConfigurationProperties(EXECUTOR_CONFIG_PREFIX)
public class LibreExecutorProperties {
	/**
	 * 异步核心线程数，默认：2
	 */
	private int corePoolSize = 2;
	/**
	 * 异步最大线程数，默认：50
	 */
	private int maxPoolSize = 50;
	/**
	 * 队列容量，默认：10000
	 */
	private int queueCapacity = 10000;
	/**
	 * 线程存活时间，默认：300
	 */
	private int keepAliveSeconds = 300;
}

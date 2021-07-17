package com.libre.captcha.cache;

import com.libre.core.toolkit.CharPool;
import com.libre.core.toolkit.StringPool;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author ZC
 * @date 2021/7/17 1:57
 */
public interface CaptchaCache {

	/**
	 * 保存缓存
	 *
	 * <p>
	 *     非 spring cache 等启动就确定超时的缓存，重新改方法
	 * </p>
	 *
	 * @param cacheKey    缓存key
	 * @param value       缓存value
	 * @param ttlInMillis ttl
	 */
	default void put(String cacheKey, @Nullable String value, long ttlInMillis) {

	}

	/**
	 * 保存缓存
	 *
	 * @param cacheName 缓存空间
	 * @param uuid      验证码 uuid
	 * @param value     缓存value
	 */
	default void put(String cacheName, String uuid, @Nullable String value) {
		long ttlInMillis = getTtlFormCacheName(cacheName);
		String cacheKey = cacheName + CharPool.COLON + uuid;
		put(cacheKey, value, ttlInMillis);
	}


	/**
	 * 从 cache name 中解析 ttl，例如： user:test#300ms，不带单位默认为 s 秒
	 *
	 * @param cacheName 缓存名
	 * @return 超时时间
	 */
	 default long getTtlFormCacheName(String cacheName) {
		String[] cacheArray = cacheName.split(StringPool.HASH);
		if (cacheArray.length < 2) {
			return -1L;
		}
		Duration duration = DurationStyle.detectAndParse(cacheArray[1], ChronoUnit.SECONDS);
		return duration.toMillis();
	}

	/**
	 * 获取并删除缓存，验证码不管成功只能验证一次
	 *
	 * <p>
	 *     非 spring cache 等启动就确定超时的缓存，重新改方法
	 * </p>
	 *
	 * @param cacheKey 缓存空间
	 * @return 验证码
	 */
	@Nullable
	default String getAndRemove(String cacheKey) {
		return null;
	}

	/**
	 * 获取并删除缓存，验证码不管成功只能验证一次
	 *
	 * @param cacheName 缓存空间
	 * @param uuid      验证码 uuid
	 * @return 验证码
	 */
	@Nullable
	default String getAndRemove(String cacheName, String uuid) {
		String cacheKey = cacheName + CharPool.COLON + uuid;
		return getAndRemove(cacheKey);
	}
}

package com.libre.captcha.cache;

import com.libre.captcha.config.CaptchaProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @author ZC
 * @date 2021/7/17 2:28
 */
@RequiredArgsConstructor
public class SpringCacheCaptchaCache implements CaptchaCache {

	private final CacheManager cacheManager;
	private final CaptchaProperties properties;

	@PostConstruct
	public void init() {
		String cacheName = properties.getCacheName();
		Cache cache = cacheManager.getCache(cacheName);
		Objects.requireNonNull(cache, "libre-captcha spring cache name " + cacheName + " is null.");
	}

	@Override
	public void put(String cacheName, String uuid, String value) {
		Cache cache = getCache(cacheName);
		cache.put(uuid, value);
	}

	@Override
	public String getAndRemove(String cacheName, String uuid) {
		Cache captchaCache = getCache(cacheName);
		String value = captchaCache.get(uuid, String.class);
		if (value != null) {
			captchaCache.evict(uuid);
		}
		return value;
	}

	private Cache getCache(String cacheName) {
		return cacheManager.getCache(cacheName);
	}
}

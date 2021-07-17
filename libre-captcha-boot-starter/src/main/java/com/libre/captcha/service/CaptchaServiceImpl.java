package com.libre.captcha.service;

import cn.hutool.core.util.StrUtil;
import com.libre.captcha.cache.CaptchaCache;
import com.libre.captcha.config.CaptchaProperties;
import com.libre.captcha.vo.CaptchaVO;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;

/**
 * @author ZC
 * @date 2021/7/17 1:49
 */
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {

	private final Captcha captcha;
	private final CaptchaCache captchaCache;
	private final CaptchaProperties properties;

	@Override
	public void generate(String uuid) {
		String text = captcha.text();
		captchaCache.put(properties.getCacheName(), uuid, text);
	}

	@Override
	public CaptchaVO generateBase64Vo(String uuid) {
		generate(uuid);
		return new CaptchaVO(uuid, captcha.toBase64());
	}

	@Override
	public boolean validate(String uuid, String userInputCaptcha) {
		String code = captchaCache.getAndRemove(properties.getCacheName(), uuid);
		if (StrUtil.isBlank(code)) {
			return false;
		}
		return code.equalsIgnoreCase(userInputCaptcha);
	}
}

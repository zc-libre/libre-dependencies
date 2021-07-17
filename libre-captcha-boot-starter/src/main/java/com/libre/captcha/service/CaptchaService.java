package com.libre.captcha.service;

import com.libre.captcha.vo.CaptchaVO;
import com.wf.captcha.base.Captcha;

/**
 * @author ZC
 * @date 2021/7/17 0:57
 */
public interface CaptchaService {

	/**
	 * 生成验证码
	 *
	 * @param uuid         自定义缓存的 uuid
	 */
	void generate(String uuid);


	/**
	 * 生成验证码 base64 CaptchaVO
	 *
	 * @param uuid 自定义缓存的 uuid
	 * @return CaptchaVO
	 */
	 CaptchaVO generateBase64Vo(String uuid);


	/**
	 * 校验验证码
	 *
	 * @param uuid             自定义缓存的 uuid
	 * @param userInputCaptcha 用户输入的图形验证码
	 * @return 是否校验成功
	 */
	boolean validate(String uuid, String userInputCaptcha);
}

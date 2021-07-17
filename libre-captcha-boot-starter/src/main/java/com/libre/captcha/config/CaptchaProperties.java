package com.libre.captcha.config;

import com.wf.captcha.base.Captcha;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.awt.*;

/**
 * @author ZC
 * @date 2021/7/17 0:58
 */
@Data
@ConfigurationProperties("libre.captcha")
public class CaptchaProperties {

	private Boolean enabled = Boolean.TRUE;
	/**
	 * 验证码内容长度
	 */
	private Integer length = 2;
	/**
	 * 验证码宽度
	 */
	private Integer width = 111;
	/**
	 * 验证码高度
	 */
	private Integer height = 36;
	/**
	 * 缓存key
	 */
	private String cacheName = "captcha:cache#5m";


	private CaptchaType captchaType = CaptchaType.ARITHMETIC;

	private enum CaptchaType {
		/**
		 * 算数
		 */
		ARITHMETIC,
		/**
		 * 中文
		 */
		CHINESE,
		/**
		 * 中文闪图
		 */
		CHINESE_GIF,
		/**
		 * 闪图
		 */
		GIF,
		/**
		 * png
		 */
		SPEC;
	}
}

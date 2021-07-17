package com.libre.captcha.vo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author ZC
 * @date 2021/7/17 1:42
 */
@Data
@RequiredArgsConstructor
public class CaptchaVO {
	private final String uuid;
	private final String base64;
}

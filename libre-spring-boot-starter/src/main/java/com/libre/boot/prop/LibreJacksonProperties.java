package com.libre.boot.prop;


import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.libre.boot.constant.BootConstant.JACKSON_CONFIG_PREFIX;

/**
 * jackson 配置
 *
 * @author zhao.cheng
 */
@ConfigurationProperties(JACKSON_CONFIG_PREFIX)
public class LibreJacksonProperties {

	/**
	 * null 转为 空，字符串转成""，数组转为[]，对象转为{}，数字转为-1
	 */
	private Boolean nullToEmpty = Boolean.TRUE;
	/**
	 * 响应到前端，大数值自动写出为 String，避免精度丢失
	 */
	private Boolean bigNumToString = Boolean.TRUE;
	/**
	 * 支持 MediaType text/plain
	 */
	private Boolean supportTextPlain = Boolean.FALSE;


	public Boolean getNullToEmpty() {
		return nullToEmpty;
	}

	public void setNullToEmpty(Boolean nullToEmpty) {
		this.nullToEmpty = nullToEmpty;
	}

	public Boolean getBigNumToString() {
		return bigNumToString;
	}

	public void setBigNumToString(Boolean bigNumToString) {
		this.bigNumToString = bigNumToString;
	}

	public Boolean getSupportTextPlain() {
		return supportTextPlain;
	}

	public void setSupportTextPlain(Boolean supportTextPlain) {
		this.supportTextPlain = supportTextPlain;
	}
}

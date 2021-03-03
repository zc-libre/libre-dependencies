
package com.libre.boot.prop;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

import static com.libre.boot.constant.BootConstant.XSS_CONFIG_PREFIX;

/**
 * Xss配置类
 *
 * @author zc
 */
@Data
@ConfigurationProperties(XSS_CONFIG_PREFIX)
public class XssProperties {

	/**
	 * 开启xss
	 */
	private Boolean enabled = true;

	/**
	 * 放行url
	 */
	private List<String> skipUrl = Lists.newArrayList();

}

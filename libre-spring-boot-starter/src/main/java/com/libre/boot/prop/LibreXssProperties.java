package com.libre.boot.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Xss配置类
 *
 * @author L.cm
 */
@Getter
@Setter
@ConfigurationProperties("mica.xss")
public class LibreXssProperties {

	/**
	 * 开启xss
	 */
	private boolean enabled = true;
	/**
	 * 拦截的路由，默认为空
	 */
	private List<String> pathPatterns = new ArrayList<>();
	/**
	 * 放行的规则，默认为空
	 */
	private List<String> excludePatterns = new ArrayList<>();

}

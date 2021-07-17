

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
@ConfigurationProperties(LibreXssProperties.PREFIX)
public class LibreXssProperties {
	public static final String PREFIX = "libre.xss";

	/**
	 * 开启xss
	 */
	private boolean enabled = Boolean.TRUE;
	/**
	 * 全局：对文件进行首尾 trim
	 */
	private boolean trimText = true;
	/**
	 * 模式：clear 清理（默认），escape 转义
	 */
	private Mode mode = Mode.clear;
	/**
	 * [clear 专用] prettyPrint，默认关闭： 保留换行
	 */
	private boolean prettyPrint = false;
	/**
	 * [clear 专用] 使用转义，默认关闭
	 */
	private boolean enableEscape = false;
	/**
	 * 拦截的路由，默认为空
	 */
	private List<String> pathPatterns = new ArrayList<>();
	/**
	 * 放行的路由，默认为空
	 */
	private List<String> pathExcludePatterns = new ArrayList<>();

	public enum Mode {
		/**
		 * 清理
		 */
		clear,
		/**
		 * 转义
		 */
		escape;
	}

}

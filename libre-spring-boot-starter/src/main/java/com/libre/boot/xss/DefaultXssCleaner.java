package com.libre.boot.xss;

import cn.hutool.core.util.StrUtil;

import com.libre.boot.prop.LibreXssProperties;
import com.libre.core.toolkit.XssUtil;
import lombok.RequiredArgsConstructor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.jsoup.safety.Cleaner;
import org.springframework.web.util.HtmlUtils;

import java.nio.charset.StandardCharsets;

/**
 * 默认的 xss 清理器
 *
 * @author L.cm
 */
@RequiredArgsConstructor
public class DefaultXssCleaner implements XssCleaner {
	private final LibreXssProperties properties;

	@Override
	public String clean(String bodyHtml) {
		// 1. 为空直接返回
		if (StrUtil.isBlank(bodyHtml)) {
			return bodyHtml;
		}
		LibreXssProperties.Mode mode = properties.getMode();
		if (LibreXssProperties.Mode.escape == mode) {
			// html 转义
			return HtmlUtils.htmlEscape(bodyHtml, StandardCharsets.UTF_8.name());
		} else {
			// jsoup html 清理
			Document.OutputSettings outputSettings = new Document.OutputSettings()
				// 2. 转义，没找到关闭的方法，目前这个规则最少
				.escapeMode(Entities.EscapeMode.xhtml)
				// 3. 保留换行
				.prettyPrint(properties.isPrettyPrint());
			Document dirty = Jsoup.parseBodyFragment(bodyHtml, "");
			Cleaner cleaner = new Cleaner(XssUtil.WHITE_LIST);
			Document clean = cleaner.clean(dirty);
			clean.outputSettings(outputSettings);
			// 4. 清理后的 html
			String escapedHtml = clean.body().html();
			if (properties.isEnableEscape()) {
				return escapedHtml;
			}
			// 5. 反转义
			return Entities.unescape(escapedHtml);
		}
	}

}

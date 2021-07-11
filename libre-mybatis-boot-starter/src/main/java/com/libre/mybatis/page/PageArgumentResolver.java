package com.libre.mybatis.page;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.libre.core.toolkit.StringPool;
import com.libre.core.toolkit.StringUtil;
import com.libre.mybatis.prop.LibreMyBatisProperties;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

/**
 * @author ZC
 * @date 2021/7/11 11:12
 */
@RequiredArgsConstructor
public class PageArgumentResolver implements HandlerMethodArgumentResolver {

	private static final String ORDER_ASC = "asc";
	private final LibreMyBatisProperties myBatisProperties;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return Page.class.equals(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
								  ModelAndViewContainer modelAndViewContainer,
								  NativeWebRequest request,
								  WebDataBinderFactory webDataBinderFactory) throws Exception {

		LibreMyBatisProperties.Page pageConfig = myBatisProperties.getPage();
		String currentField = pageConfig.getCurrentField();
        // 分页参数 page: 0, size: 10, sort=id%2Cdesc
		String pageParam = request.getParameter(currentField);
		String sizeParam = request.getParameter(pageConfig.getSizeField());
		String[] sortParam = request.getParameterValues(pageConfig.getSortField());

		Page<?> page = new Page<>();
        page.setCurrent(Convert.toLong(pageParam));
        page.setSize(Convert.toLong(sizeParam));

		if (Objects.isNull(sortParam)) {
			return page;
		}
		for (String param : sortParam) {
			if (StrUtil.isBlank(param)) {
				continue;
			}
			String[] split = param.split(StringPool.COMMA);
			// 清理字符串
			OrderItem orderItem = new OrderItem();
			orderItem.setColumn(StringUtil.cleanIdentifier(split[0]));
			orderItem.setAsc(isOrderAsc(split));
			page.addOrder(orderItem);
		}
        // 分页溢出的情况大于 long 最大值
		if (page.offset() < 0) {
			throw new IllegalArgumentException("Paging parameter exceeds the maximum.");
		}
		return page;
	}

	private static boolean isOrderAsc(String[] split) {
		// 默认 desc
		if (split.length < 2) {
			return false;
		}
		return ORDER_ASC.equalsIgnoreCase(split[1]);
	}


}

package com.libre.boot.xss;

import com.libre.boot.prop.XssProperties;
import com.libre.core.tookit.StringPool;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Request全局过滤
 *
 * @author zc
 */

public class RequestFilter implements Filter {

	private final XssProperties xssProperties;

	public RequestFilter(XssProperties xssProperties) {
		this.xssProperties = xssProperties;
	}

	@Override
	public void init(FilterConfig config) {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String path = ((HttpServletRequest) request).getServletPath();
		if (!xssProperties.getEnabled() || isSkip(path)) {
			HttpServletRequestWrapper bladeRequest = new HttpServletRequestWrapper((HttpServletRequest) request);
			chain.doFilter(bladeRequest, response);
		} else {
			XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
			chain.doFilter(xssRequest, response);
		}
	}

	private boolean isSkip(String path) {
		return xssProperties.getSkipUrl().stream().map(url -> url.replace("/**", StringPool.EMPTY)).anyMatch(path::startsWith);
	}

	@Override
	public void destroy() {

	}

}

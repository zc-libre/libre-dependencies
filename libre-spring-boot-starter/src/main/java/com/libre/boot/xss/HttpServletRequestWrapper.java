package com.libre.boot.xss;

import com.libre.core.tookit.WebUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 全局Request包装
 *
 * @author zc
 */
public class HttpServletRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper {

	/**
	 * 没被包装过的HttpServletRequest（特殊场景,需要自己过滤）
	 */
	private final HttpServletRequest orgRequest;
	/**
	 * 缓存报文,支持多次读取流
	 */
	private byte[] body;


	public HttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		orgRequest = request;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (super.getHeader(HttpHeaders.CONTENT_TYPE) == null) {
			return super.getInputStream();
		}

		if (super.getHeader(HttpHeaders.CONTENT_TYPE).startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
			return super.getInputStream();
		}

		if (body == null) {
			body = WebUtil.getRequestBody(super.getInputStream()).getBytes();
		}

		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);

		return new ServletInputStream() {

			@Override
			public int read() {
				return byteArrayInputStream.read();
			}

			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
			}
		};
	}

	/**
	 * 获取初始request
	 *
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getOrgRequest() {
		return orgRequest;
	}

	/**
	 * 获取初始request
	 *
	 * @param request request
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getOrgRequest(HttpServletRequest request) {
		if (request instanceof HttpServletRequestWrapper) {
			return ((HttpServletRequestWrapper) request).getOrgRequest();
		}
		return request;
	}

}

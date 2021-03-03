package com.libre.boot.exception;

import com.libre.core.result.IResultCode;
import com.libre.core.result.ResultCode;
import lombok.Getter;



/**
 * 业务异常
 * @author zhao.cheng
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 2359767895161832954L;

	@Getter
	private final IResultCode resultCode;

	public ServiceException(String message) {
		super(message);
		this.resultCode = ResultCode.FAILURE;
	}

	public ServiceException(IResultCode resultCode) {
		super(resultCode.getMessage());
		this.resultCode = resultCode;
	}

	public ServiceException(IResultCode resultCode, Throwable cause) {
		super(cause);
		this.resultCode = resultCode;
	}

	/**
	 * 提高性能
	 *
	 * @return Throwable
	 */
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

	public Throwable doFillInStackTrace() {
		return super.fillInStackTrace();
	}

}

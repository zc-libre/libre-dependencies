package com.libre.boot.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * 异常类型
 *
 * @author L.cm
 */
@Getter
@RequiredArgsConstructor
public enum ErrorType {
	/**
	 * rest 请求异常
	 */
	REQUEST("request"),
	/**
	 * 异步异常
	 */
	ASYNC("async"),
	/**
	 * 定时任务异常
	 */
	SCHEDULER("scheduler"),
	/**
	 * websocket 异常
	 */
	WEB_SOCKET("websocket"),
	/**
	 * 其他异常
	 */
	OTHER("other");

	@JsonValue
	private final String type;

	@Nullable
	@JsonCreator
	public static ErrorType of(String type) {
		ErrorType[] values = ErrorType.values();
		for (ErrorType errorType : values) {
			if (errorType.type.equals(type)) {
				return errorType;
			}
		}
		return null;
	}

}

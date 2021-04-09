package com.libre.boot.executor;

import com.libre.core.toolkit.ThreadLocalUtil;
import org.slf4j.MDC;

import org.springframework.lang.Nullable;

import java.util.Map;

/**
 * 多线程中传递 context 和 mdc
 *
 * @author zhao.cheng
 */
public class RunnableWrapper implements Runnable {
	private final Runnable delegate;
	private final Map<String, Object> tlMap;
	/**
	 * logback 下有可能为 null
	 */
	@Nullable
	private final Map<String, String> mdcMap;

	public RunnableWrapper(Runnable runnable) {
		this.delegate = runnable;
		this.tlMap = ThreadLocalUtil.getAll();
		this.mdcMap = MDC.getCopyOfContextMap();
	}

	@Override
	public void run() {
		if (!tlMap.isEmpty()) {
			ThreadLocalUtil.put(tlMap);
		}
		if (mdcMap != null && !mdcMap.isEmpty()) {
			MDC.setContextMap(mdcMap);
		}
		try {
			delegate.run();
		} finally {
			tlMap.clear();
			if (mdcMap != null) {
				mdcMap.clear();
			}
			ThreadLocalUtil.clear();
			MDC.clear();
		}
	}
}

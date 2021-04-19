package com.libre.core.toolkit;

import cn.hutool.core.io.FastStringWriter;

import java.io.PrintWriter;

public class FastStringPrintWriter extends PrintWriter {
	private final FastStringWriter writer;

	public FastStringPrintWriter() {
		this(256);
	}

	public FastStringPrintWriter(int initialSize) {
		super(new FastStringWriter(initialSize));
		this.writer = (FastStringWriter) out;
	}

	/**
	 * Throwable printStackTrace，只掉用了该方法
	 *
	 * @param x Object
	 */
	@Override
	public void println(Object x) {
		writer.write(String.valueOf(x));
		writer.write(CharPool.NEWLINE);
	}

	@Override
	public String toString() {
		return writer.toString();
	}
}

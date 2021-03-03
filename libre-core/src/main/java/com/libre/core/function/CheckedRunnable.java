

package com.libre.core.function;

import java.io.Serializable;

/**
 * 受检的 runnable
 *

 */
@FunctionalInterface
public interface CheckedRunnable extends Serializable {

	/**
	 * Run this runnable.
	 *
	 * @throws Throwable CheckedException
	 */
	void run() throws Throwable;

}



package com.libre.core.function;

import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * 受检的 function
 *

 */
@FunctionalInterface
public interface CheckedFunction<T, R> extends Serializable {

	/**
	 * Run the Function
	 *
	 * @param t T
	 * @return R R
	 * @throws Throwable CheckedException
	 */
	@Nullable
	R apply(@Nullable T t) throws Throwable;

}

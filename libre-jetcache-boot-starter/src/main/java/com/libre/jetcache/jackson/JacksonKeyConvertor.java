

package com.libre.jetcache.jackson;


import com.alicp.jetcache.support.CacheEncodeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

/**
 * JacksonKey Convertor
 *
 * @author L.cm
 */
@RequiredArgsConstructor
public class JacksonKeyConvertor implements Function<Object, Object> {
	private final ObjectMapper mapper;

	@Override
	public Object apply(Object originalKey) {
		if (originalKey == null) {
			return null;
		}
		if (originalKey instanceof String) {
			return originalKey;
		}
		try {
			return mapper.writeValueAsString(originalKey);
		} catch (JsonProcessingException e) {
			throw new CacheEncodeException("Key convertor error", e);
		}
	}

}


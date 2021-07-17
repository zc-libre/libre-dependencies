

package com.libre.jetcache.jackson;

import com.alicp.jetcache.CacheValueHolder;
import com.alicp.jetcache.support.CacheEncodeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.function.Function;

/**
 * jackson ValueDecoder
 *
 * @author L.cm
 */
@RequiredArgsConstructor
public class JacksonValueDecoder implements Function<byte[], Object> {
	private final ObjectMapper mapper;

	@Override
	public Object apply(byte[] bytes) {
		try {
			return mapper.readValue(bytes, CacheValueHolder.class);
		} catch (IOException e) {
			throw new CacheEncodeException("decode error", e);
		}
	}

}

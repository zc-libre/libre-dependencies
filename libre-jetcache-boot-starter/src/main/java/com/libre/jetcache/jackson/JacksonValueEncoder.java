

package com.libre.jetcache.jackson;

import com.alicp.jetcache.support.CacheEncodeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

/**
 * Jackson ValueEncoder
 *
 * @author L.cm
 */
@RequiredArgsConstructor
public class JacksonValueEncoder implements Function<Object, byte[]> {
	private final ObjectMapper mapper;

	@Override
	public byte[] apply(Object o) {
		try {
			return mapper.writeValueAsBytes(o);
		} catch (JsonProcessingException e) {
			throw new CacheEncodeException("decode error", e);
		}
	}

}

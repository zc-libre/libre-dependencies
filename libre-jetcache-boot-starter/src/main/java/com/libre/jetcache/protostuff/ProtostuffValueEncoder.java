

package com.libre.jetcache.protostuff;

import com.alicp.jetcache.support.CacheEncodeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libre.core.toolkit.BytesWrapper;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

/**
 * Protostuff ValueEncoder
 * @author L.cm
 */
public class ProtostuffValueEncoder implements Function<Object, byte[]> {

	@SuppressWarnings("rawtypes")
	private final Schema<BytesWrapper> schema;

	public ProtostuffValueEncoder() {
		this.schema = RuntimeSchema.getSchema(BytesWrapper.class);
	}

	@Override
	public byte[] apply(Object o) {
		if (o == null) {
			return null;
		}
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		try {
			return ProtostuffIOUtil.toByteArray(new BytesWrapper<>(o), schema, buffer);
		} finally {
			buffer.clear();
		}
	}

}

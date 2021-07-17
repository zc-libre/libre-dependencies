

package com.libre.jetcache.protostuff;

import cn.hutool.core.util.ObjectUtil;
import com.alicp.jetcache.CacheValueHolder;
import com.alicp.jetcache.support.CacheEncodeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libre.core.toolkit.BytesWrapper;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.function.Function;

/**
 * Protostuff ValueDecoder
 *
 * @author L.cm
 */

public class ProtostuffValueDecoder implements Function<byte[], Object> {
	@SuppressWarnings("rawtypes")
	private final Schema<BytesWrapper> schema;

	public ProtostuffValueDecoder() {
		this.schema = RuntimeSchema.getSchema(BytesWrapper.class);
	}

	@Override
	public Object apply(byte[] bytes) {
		if (ObjectUtil.isEmpty(bytes)) {
			return null;
		}
		BytesWrapper<Object> wrapper = new BytesWrapper<>();
		ProtostuffIOUtil.mergeFrom(bytes, wrapper, schema);
		return wrapper.getValue();
	}

}

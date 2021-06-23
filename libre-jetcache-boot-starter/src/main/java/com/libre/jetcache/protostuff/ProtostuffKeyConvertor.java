package com.libre.jetcache.protostuff;

import com.libre.core.toolkit.BytesWrapper;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.function.Function;

/**
 * @author zhao.cheng
 */
public class ProtostuffKeyConvertor implements Function<Object, Object> {

    @SuppressWarnings("rawtypes")
    private final Schema<BytesWrapper> schema;

    public ProtostuffKeyConvertor() {
        this.schema = RuntimeSchema.getSchema(BytesWrapper.class);
    }

    @Override
    public Object apply(Object originalKey) {
        if (originalKey == null) {
            return null;
        }
        if (originalKey instanceof String) {
            return originalKey;
        }

        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        byte[] bytes;
        try {
             bytes = ProtostuffIOUtil.toByteArray(new BytesWrapper<>(originalKey), schema, buffer);
        } finally {
            buffer.clear();
        }
        BytesWrapper<Object> wrapper = new BytesWrapper<>();
        ProtostuffIOUtil.mergeFrom(bytes, wrapper, schema);
        return wrapper.getValue();
    }
}

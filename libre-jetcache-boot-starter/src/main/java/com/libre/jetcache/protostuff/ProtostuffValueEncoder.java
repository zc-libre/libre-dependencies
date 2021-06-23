/*
 * Copyright (c) 2019-2029, Dreamlu 卢春梦 (596392912@qq.com & www.dreamlu.net).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

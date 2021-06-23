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

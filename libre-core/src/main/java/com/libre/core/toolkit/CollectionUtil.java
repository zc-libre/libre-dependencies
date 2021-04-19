package com.libre.core.toolkit;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhao.cheng
 * @date 2021/3/3 12:39
 */
public class CollectionUtil  extends CollUtil {

    /**
     * 将key value 数组转为 map
     *
     * @param keysValues key value 数组
     * @param <K>        key
     * @param <V>        value
     * @return map 集合
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> toGenericMap(Object... keysValues) {
        int kvLength = keysValues.length;
        if (kvLength % 2 != 0) {
            throw new IllegalArgumentException("wrong number of arguments for met, keysValues length can not be odd");
        }
        Map<K, V> keyValueMap = new HashMap<>(kvLength);
        for (int i = kvLength - 2; i >= 0; i -= 2) {
            Object key = keysValues[i];
            Object value = keysValues[i + 1];
            keyValueMap.put((K) key, (V) value);
        }
        return keyValueMap;
    }
}

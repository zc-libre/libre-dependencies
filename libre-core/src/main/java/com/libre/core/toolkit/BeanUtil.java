package com.libre.core.toolkit;

import cn.hutool.extra.cglib.CglibUtil;
import lombok.experimental.UtilityClass;
import net.sf.cglib.beans.BeanMap;
import org.springframework.objenesis.instantiator.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * @author zhao.cheng
 * @date 2021/4/16 10:32
 */
@UtilityClass
public class BeanUtil extends CglibUtil {
    /**
     * 将对象装换为 map,对象转成 map，key肯定是字符串
     *
     * @param bean 转换对象
     * @return 返回转换后的 map 对象
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> beanToMap(Object bean) {
        return null == bean ? null : BeanMap.create(bean);
    }

    /**
     * map 转换为 java bean 对象
     *
     * @param map   转换 MAP
     * @param clazz 对象 Class
     * @return 返回 bean 对象
     */
    public static <T> T mapToBean(Map<String, ?> map, Class<T> clazz) {
        T bean = ClassUtils.newInstance(clazz);
        BeanMap.create(bean).putAll(map);
        return bean;
    }

    /**
     * List&lt;T&gt; 转换为 List&lt;Map&lt;String, Object&gt;&gt;
     *
     * @param beans 转换对象集合
     * @return 返回转换后的 bean 列表
     */
    public static <T> List<Map<String, Object>> beansToMaps(List<T> beans) {
        if (CollectionUtils.isEmpty(beans)) {
            return Collections.emptyList();
        }
        return beans.stream().map(BeanUtil::beanToMap).collect(toList());
    }

    /**
     * List&lt;Map&lt;String, Object&gt;&gt; 转换为 List&lt;T&gt;
     *
     * @param maps  转换 MAP 集合
     * @param clazz 对象 Class
     * @return 返回转换后的 bean 集合
     */
    public static <T> List<T> mapsToBeans(List<? extends Map<String, ?>> maps, Class<T> clazz) {
        if (CollectionUtils.isEmpty(maps)) {
            return Collections.emptyList();
        }
        return maps.stream().map(e -> mapToBean(e, clazz)).collect(toList());
    }
}

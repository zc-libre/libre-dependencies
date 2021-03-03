package com.libre.core.mapstruct;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MapperConfig;


import java.util.List;
import java.util.stream.Stream;

/**
 * @author zhao.cheng
 */
@MapperConfig
public interface BaseConvert<SOURCE, TARGET> {

    /**
     * 映射同名属性
     * @param source /
     * @return /
     */
      TARGET sourceToTarget(SOURCE source);

    /**
     * 反向，映射同名属性
     * @param target /
     * @return /
     */
    @InheritInverseConfiguration(name = "sourceToTarget")
     SOURCE targetToSource(TARGET target);

    /**
     * 映射同名属性，集合形式
     * @param sourceList /
     * @return /
     */
    @InheritConfiguration(name = "sourceToTarget")
     List<TARGET> sourceToTarget(List<SOURCE> sourceList);

    /**
     * 反向，映射同名属性，集合形式
     * @param targetList /
     * @return /
     */
    @InheritConfiguration(name = "targetToSource")
     List<SOURCE> targetToSource(List<TARGET> targetList);

    /**
     * 映射同名属性，集合流形式
     * @param stream /
     * @return /
     */
     List<TARGET> sourceToTarget(Stream<SOURCE> stream);

    /**
     * 反向，映射同名属性，集合流形式
     * @param stream /
     * @return /
     */
     List<SOURCE> targetToSource(Stream<TARGET> stream);
}

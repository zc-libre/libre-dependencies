package com.libre.mybatis.annotation;

import com.libre.mybatis.permission.constants.DataPermissionConstants;
import com.libre.mybatis.permission.enums.DataPermissionEnum;
import java.lang.annotation.*;

/**
 * @author zhao.cheng
 * @date 2021/4/20 14:09
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DataPermission {

    /**
     * 数据权限对应字段
     */
    String column() default DataPermissionConstants.DEPT_COLUMN;

    /**
     * 数据权限类型
     * @return /
     */
    DataPermissionEnum type() default DataPermissionEnum.ALL;

    /**
     * 可见字段
     */
    String[] fields() default {"*"};

}

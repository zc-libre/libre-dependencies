package com.libre.mybatis.permission.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhao.cheng
 * @date 2021/4/20 15:19
 */
@Getter
@AllArgsConstructor
public enum DataPermissionEnum {

    /**
     *所有
     */
    ALL(1),

    /**
     * 本人可见
     */
    OWN(2),

    /**
     * 所在部门可见
     */
    OWN_DEPT(3),

    /**
     * 所在机构及子级可见
     */
    OWN_DEPT_CHILD(4),

    /**
     * 自定义
     */
    CUSTOM(5);


    private final int type;

}

package com.libre.mybatis.permission.provider;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhao.cheng
 * @date 2021/4/21 11:41
 */
@Data
@NoArgsConstructor
public class DataPermissionEntity {

    /**
     * 权限字段
     */
    private String permissionColumn;

    /**
     * 权限类型
     */
    private Integer permissionType;

    /**
     * 可见字段
     */
    private List<String> permissionFields;

    /**
     * 条件值
     */
    private List<Long> values;


    public DataPermissionEntity(String permissionColumn,
                                Integer permissionType,
                                List<String> permissionFields) {
        this.permissionColumn = permissionColumn;
        this.permissionType = permissionType;
        this.permissionFields = permissionFields;
    }

}

package com.libre.mybatis.permission.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhao.cheng
 * @date 2021/4/20 17:02
 */
@Data
@ConfigurationProperties("libre.data-permission")
public class DataPermissionProperties {

    /**
     * 是否开启数据权限
     */
    private Boolean enabled = Boolean.FALSE;


    /**
     * 数据权限对应字段
     */
    private String permissionColumn = "create_dept";

}

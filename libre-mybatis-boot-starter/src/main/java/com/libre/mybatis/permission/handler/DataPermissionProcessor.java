package com.libre.mybatis.permission.handler;

import com.libre.mybatis.permission.provider.DataPermissionEntity;

/**
 * @author zhao.cheng
 * @date 2021/4/21 11:19
 */
public interface DataPermissionProcessor {

    /**
     * 数据权限处理,使用时只需实现此接口,并注入spring容器中。
     * 根据业务项目的权限字段结合项目具体的权限查询到相应的值，比如可以查看的部门id列表等。
     * @param dataPermission 数据权限实体
     */
    void processor(DataPermissionEntity dataPermission);
}

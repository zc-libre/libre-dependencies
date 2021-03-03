package com.libre.core.constant;

/**
 * @author zhao.cheng
 * @Date 2021/2/18
 */
public interface LibreConstants {

    /**
     * contentType
     */
    String CONTENT_TYPE_NAME = "Content-type";

    /**
     * JSON 资源
     */
    String CONTENT_TYPE = "application/json;charset=utf-8";


    /**
     * 业务状态[1:正常]
     */
    int DB_STATUS_NORMAL = 1;


    /**
     * 删除状态[0:正常,1:删除]
     */
    int DB_NOT_DELETED = 0;
    int DB_IS_DELETED = 1;

    /**
     * 用户锁定状态
     */
    int DB_ADMIN_NON_LOCKED = 0;
    int DB_ADMIN_LOCKED = 1;

    /**
     * 顶级父节点id
     */
    Long TOP_PARENT_ID = 0L;

    /**
     * 顶级父节点名称
     */
    String TOP_PARENT_NAME = "顶级";

    /**
     * 管理员对应的租户ID
     */
    String ADMIN_TENANT_ID = "000000";

    /**
     * 日志默认状态
     */
    String LOG_NORMAL_TYPE = "1";

    /**
     * 默认为空消息
     */
    String DEFAULT_NULL_MESSAGE = "暂无承载数据";
    /**
     * 默认成功消息
     */
    String DEFAULT_SUCCESS_MESSAGE = "操作成功";
    /**
     * 默认失败消息
     */
    String DEFAULT_FAILURE_MESSAGE = "操作失败";
    /**
     * 默认未授权消息
     */
    String DEFAULT_UNAUTHORIZED_MESSAGE = "签名认证失败";
}

package com.libre.redis.lock;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhao.cheng
 * @date 2021/5/20 10:21
 */
@Data
@ConfigurationProperties(prefix = "libre.lock")
public class LockProperties {

    /**
     * 是否开启：默认为：false，便于生成配置提示。
     */
    private Boolean enabled = Boolean.FALSE;
    /**
     * 单机配置：redis 服务地址
     */
    private String address = "redis://127.0.0.1:6379";
    /**
     * 密码配置
     */
    private String password;
    /**
     * db
     */
    private Integer database = 0;
    /**
     * 连接池大小
     */
    private Integer poolSize = 20;
    /**
     * 最小空闲连接数
     */
    private Integer idleSize = 5;
    /**
     * 连接空闲超时，单位：毫秒
     */
    private Integer idleTimeout = 60000;
    /**
     * 连接超时，单位：毫秒
     */
    private Integer connectionTimeout = 3000;
    /**
     * 命令等待超时，单位：毫秒
     */
    private Integer timeout = 10000;
    /**
     * 集群模式，单机：single，主从：master，哨兵模式：sentinel，集群模式：cluster
     */
    private Mode mode = Mode.SINGLE;
    /**
     * 主从模式，主地址
     */
    private String masterAddress;
    /**
     * 主从模式，从地址
     */
    private String[] slaveAddress;
    /**
     * 哨兵模式：主名称
     */
    private String masterName;
    /**
     * 哨兵模式地址
     */
    private String[] sentinelAddress;
    /**
     * 集群模式节点地址
     */
    private String[] nodeAddress;

    public enum Mode {
        /**
         * 集群模式，单机：single，主从：master，哨兵模式：sentinel，集群模式：cluster
         */
        SINGLE,

        MASTER,

        SENTINEL,

        CLUSTER;
    }
}

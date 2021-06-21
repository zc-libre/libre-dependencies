package com.libre.redis.lock;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhao.cheng
 * @date 2021/5/20 11:24
 */
@Slf4j
@Configuration
@ConditionalOnClass(RedissonClient.class)
@EnableConfigurationProperties(LockProperties.class)
@ConditionalOnProperty(value = "libre.lock.enabled", havingValue = "true")
public class RedisLockAutoConfiguration {

    private static Config singleConfig(LockProperties properties) {
        Config config = new Config();
        SingleServerConfig serversConfig = config.useSingleServer();
        serversConfig.setAddress(properties.getAddress());
        String password = properties.getPassword();
        if (CharSequenceUtil.isNotBlank(password)) {
            serversConfig.setPassword(password);
        }
        serversConfig.setDatabase(properties.getDatabase());
        serversConfig.setConnectionPoolSize(properties.getPoolSize());
        serversConfig.setConnectionMinimumIdleSize(properties.getIdleSize());
        serversConfig.setIdleConnectionTimeout(properties.getConnectionTimeout());
        serversConfig.setConnectTimeout(properties.getConnectionTimeout());
        serversConfig.setTimeout(properties.getTimeout());
        log.info("redis lock init type: single");
        return config;
    }

    private static Config masterSlaveConfig(LockProperties properties) {
        Config config = new Config();
        MasterSlaveServersConfig serversConfig = config.useMasterSlaveServers();
        serversConfig.setMasterAddress(properties.getMasterAddress());
        serversConfig.addSlaveAddress(properties.getSlaveAddress());
        String password = properties.getPassword();
        if (CharSequenceUtil.isNotBlank(password)) {
            serversConfig.setPassword(password);
        }
        serversConfig.setDatabase(properties.getDatabase());
        serversConfig.setMasterConnectionPoolSize(properties.getPoolSize());
        serversConfig.setMasterConnectionMinimumIdleSize(properties.getIdleSize());
        serversConfig.setSlaveConnectionPoolSize(properties.getPoolSize());
        serversConfig.setSlaveConnectionMinimumIdleSize(properties.getIdleSize());
        serversConfig.setIdleConnectionTimeout(properties.getConnectionTimeout());
        serversConfig.setConnectTimeout(properties.getConnectionTimeout());
        serversConfig.setTimeout(properties.getTimeout());
        log.info("redis lock init type: masterSlave");
        return config;
    }

    private static Config sentinelConfig(LockProperties properties) {
        Config config = new Config();
        SentinelServersConfig serversConfig = config.useSentinelServers();
        serversConfig.setMasterName(properties.getMasterName());
        serversConfig.addSentinelAddress(properties.getSentinelAddress());
        String password = properties.getPassword();
        if (CharSequenceUtil.isNotBlank(password)) {
            serversConfig.setPassword(password);
        }
        serversConfig.setDatabase(properties.getDatabase());
        serversConfig.setMasterConnectionPoolSize(properties.getPoolSize());
        serversConfig.setMasterConnectionMinimumIdleSize(properties.getIdleSize());
        serversConfig.setSlaveConnectionPoolSize(properties.getPoolSize());
        serversConfig.setSlaveConnectionMinimumIdleSize(properties.getIdleSize());
        serversConfig.setIdleConnectionTimeout(properties.getConnectionTimeout());
        serversConfig.setConnectTimeout(properties.getConnectionTimeout());
        serversConfig.setTimeout(properties.getTimeout());
        log.info("redis lock init type: sentinel");
        return config;
    }

    private static Config clusterConfig(LockProperties properties) {
        Config config = new Config();
        ClusterServersConfig serversConfig = config.useClusterServers();
        serversConfig.addNodeAddress(properties.getNodeAddress());
        String password = properties.getPassword();
        if (CharSequenceUtil.isNotBlank(password)) {
            serversConfig.setPassword(password);
        }
        serversConfig.setMasterConnectionPoolSize(properties.getPoolSize());
        serversConfig.setMasterConnectionMinimumIdleSize(properties.getIdleSize());
        serversConfig.setSlaveConnectionPoolSize(properties.getPoolSize());
        serversConfig.setSlaveConnectionMinimumIdleSize(properties.getIdleSize());
        serversConfig.setIdleConnectionTimeout(properties.getConnectionTimeout());
        serversConfig.setConnectTimeout(properties.getConnectionTimeout());
        serversConfig.setTimeout(properties.getTimeout());
        log.info("redis lock init type: cluster");
        return config;
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisLockClient redisLockClient(LockProperties properties) {
        return new RedisLockClientImpl(redissonClient(properties));
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisLockAspect redisLockAspect(RedisLockClient redisLockClient) {
        return new RedisLockAspect(redisLockClient);
    }

    private static RedissonClient redissonClient(LockProperties properties) {
        LockProperties.Mode mode = properties.getMode();
        Config config;
        switch (mode) {
            case SENTINEL:
                config = sentinelConfig(properties);
                break;
            case CLUSTER:
                config = clusterConfig(properties);
                break;
            case MASTER:
                config = masterSlaveConfig(properties);
                break;
            case SINGLE:
                config = singleConfig(properties);
                break;
            default:
                config = new Config();
                break;
        }
        return Redisson.create(config);
    }
}

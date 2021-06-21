package com.libre.redis.lock;

import com.libre.core.function.CheckedSupplier;
import com.libre.core.toolkit.Exceptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @author zhao.cheng
 * @date 2021/5/20 10:35
 */
@Slf4j
@RequiredArgsConstructor
public class RedisLockClientImpl implements RedisLockClient{

    private final RedissonClient redissonClient;

    @Override
    public boolean tryLock(String lockName, LockType lockType, long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException {
        RLock lock = getLock(lockName, lockType);
        return lock.tryLock(waitTime, leaseTime, timeUnit);
    }

    @Override
    public void unLock(String lockName, LockType lockType) {
        RLock lock = getLock(lockName, lockType);
        lock.unlock();
    }

    @Override
    public <T> T lock(String lockName, LockType lockType, long waitTime, long leaseTime, TimeUnit timeUnit, CheckedSupplier<T> supplier) {
        try {
            boolean result = this.tryLock(lockName, lockType, waitTime, leaseTime, timeUnit);
            if (result) {
                return supplier.get();
            }
        } catch (Throwable e ) {
            throw Exceptions.unchecked(e);
        } finally {
            this.unLock(lockName, lockType);
        }
        return null;
    }

    public RLock getLock(String lockName, LockType lockType) {
        RLock rLock;
        if (LockType.REENTRANT == lockType) {
            rLock = redissonClient.getLock(lockName);
        } else {
            rLock = redissonClient.getFairLock(lockName);
        }
        return rLock;
    }
}

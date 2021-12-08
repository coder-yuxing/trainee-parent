package com.yuxing.trainee.common.lock.redis;

import com.yuxing.trainee.common.lock.DistributedLock;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * 基于 redis 的分布式锁实现
 *
 * @author yuxing
 */
@Slf4j
public class RedisDistributedLock implements DistributedLock {

    /**
     * 获取锁 lua脚本：尝试设置 key-value 并设置过期时间，成功则获取锁
     */
    private static final String GET_LOCK_SCRIPT = "if redis.call('set', KEYS[1], ARGV[1], 'EX', ARGV[2], 'NX') then return 1 else return 0 end";

    /**
     *  释放锁 lua脚本：比较当前持有锁的客户端与目标对象是否一致，若一致则释放锁
     */
    private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    /**
     * 锁续期 lua脚本：当前持有锁的客户端与续期目标一致时(比较lockId)，才对当前锁进行续期
     */
    private static final String RENEW_EXPIRATION_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[2] then redis.call('expire', KEYS[1], ARGV[1]); return 1 else return 0 end";

    /**
     * 锁的默认过期时间,默认30s
     */
    private static final long DEFAULT_EXPIRE_TIME = 30;

    /**
     * 获取锁的等待时间,默认10s
     */
    private static final long DEFAULT_ACQUIRE_TIME = 10 * 1000;

    /**
     * redis 客户端
     */
    private final RedisClient redisClient;

    /**
     * 分布式锁的key值
     */
    private final String lockKey;

    /**
     * 锁超时时间：默认30s
     */
    private long expireTime = DEFAULT_EXPIRE_TIME;

    /**
     * 锁等待时间, 避免长时间等待其他线程释放资源，默认10s
     */
    private long acquireTimeout = DEFAULT_ACQUIRE_TIME;

    public RedisDistributedLock(RedisClient redisClient, String lockKey) {
        this.redisClient = redisClient;
        this.lockKey = lockKey;
    }

    public RedisDistributedLock(RedisClient redisClient, String lockKey, long expireTime) {
        this(redisClient, lockKey);
        this.expireTime = expireTime;
    }

    public RedisDistributedLock(RedisClient redisClient, String lockKey, long expireTime, long acquireTimeout) {
        this(redisClient, lockKey, expireTime);
        this.acquireTimeout = acquireTimeout;
    }

    @Override
    public String acquire() {
        try {
            // 生成当前客户端唯一标识
            String lockId = UUID.randomUUID().toString();
            long endTime = System.currentTimeMillis() + acquireTimeout;
            do {
                boolean success = redisClient.eval(GET_LOCK_SCRIPT, Collections.singletonList(this.lockKey), lockId, String.valueOf(expireTime));
                if (success) {
                    log.info("acquire lock success, lockId:{}.", lockId);
                    // 获取锁成功后，开启定时器，进行自动续期，并返回当前获取锁的唯一标识
                    this.scheduleExpirationRenewal(lockId);
                    return lockId;
                }
            // 获取锁耗费时间超出等待时间上限前，不断轮询进行获取
            } while (System.currentTimeMillis() <= endTime);
        } catch (Exception e) {
            log.error("acquire lock failed: {}.", e.getMessage());
        }
        log.warn("acquire lock failed: {}.", Thread.currentThread().getName());
        return null;
    }

    @Override
    public boolean release(String lockId) {
        try {
            boolean success = redisClient.eval(RELEASE_LOCK_SCRIPT, Collections.singletonList(lockKey), lockId);
            if (success) {
                log.info("release lock success, lockId:{}.", lockId);
                return true;
            }
        } catch (Exception e) {
            log.error("release lock failed: {}, lockId: {}.", e.getMessage(), lockId);
        }
        return false;
    }

    /**
     * 自动续期
     *
     * @param lockId 锁ID
     */
    private void scheduleExpirationRenewal(String lockId) {
        // 续期前检查是否有客户端持有锁，且持有锁的客户端需目标是否一致，若无人持有锁/目标不一致则放弃续期
        String nowLockId = redisClient.get(this.lockKey);
        if (nowLockId == null || !nowLockId.equals(lockId)) {
            return;
        }
        this.renewExpiration(lockId);
    }

    private void renewExpiration(String lockId) {
        Timer timer = new Timer(true);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
            // 续期前检查是否有客户端持有锁，且持有锁的客户端需目标是否一致，若无人持有锁/目标不一致则放弃续期
            String nowLockId = redisClient.get(lockKey);
            if (nowLockId == null || !nowLockId.equals(lockId)) {
                return;
            }

            // 续期成功则开启下次监听
            try {
                boolean success = redisClient.eval(RENEW_EXPIRATION_SCRIPT, Collections.singletonList(lockKey), String.valueOf(expireTime), lockId);
                if (success) {
                    log.info("renewExpiration success, lockId: {}.", lockId);
                    renewExpiration(lockId);
                } else {
                    log.warn("renewExpiration failed, lockId: {} maybe released.", lockId);
                }
            } catch (Exception e) {
                log.error("renewExpiration failed: {}, lockId: {}.", e.getMessage(), lockId);
            }
            }
        };
        timer.schedule(timerTask, expireTime / 3 * 1000);
    }
}

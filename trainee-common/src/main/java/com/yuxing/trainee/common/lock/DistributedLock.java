package com.yuxing.trainee.common.lock;

/**
 * 分布式锁接口
 * <br>
 * 分布式锁实现要点：<br>
 * 1. 互斥性：在任意时刻，只能有一个客户端持有锁 <br>
 * 2. 不能发生死锁：即使在客户端持有锁的期间崩溃没有主动释放锁，也需要保证后续其他客户端能够成功获取锁 <br>
 * 3. 加锁和解锁必须是同一客户端，客户端不能释放其他客户端所加的锁 <br>
 * 4. 容错性: 只要大部分redis/zk节点能够正常运行，客户端就可以进行加锁和解锁操作 <br>
 *
 * @author yuxing
 */
public interface DistributedLock {

    /**
     * 获取锁
     *
     * @return 锁标识lockId
     */
    String acquire();

    /**
     * 释放锁
     * @param lockId 锁标识
     * @return 释放锁状态： true 锁释放成功 false 锁释放失败
     */
    boolean release(String lockId);
}

package com.yuxing.trainee.common.concurrent;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * volatile 关键字特性测试
 *
 * @author yuxing
 * @since 2021/12/15
 */
public class VolatileTest {

    private volatile int count;
    private AtomicInteger atomicCount = new AtomicInteger(0);
    private LongAdder atomicCount1 = new LongAdder();

    /**
     * volatile 变量在多线程环境下仅能保证可见性，无法保证原子性，
     * 对于一写多度的场景，是可以解决变量同步问题的，但在多写场景下是无法保证线程安全的。
     *
     * 阿里规范同推荐在多写场景下类似 i++ 操作使用原子类替代 JDK8+，高并发场景下推荐使用 LongAdder(性能更好)
     */
    @Test
    public void test() {

        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                count++;
                atomicCount.incrementAndGet();
                atomicCount1.increment();
            }
        });

        thread.start();


        for (int i = 0; i < 10000; i++) {
            count--;
            atomicCount.decrementAndGet();
            atomicCount1.decrement();
        }

        while (thread.isAlive()) {}

        Assert.assertNotEquals(0, count);
        Assert.assertEquals(0, atomicCount.get());
        Assert.assertEquals(0, atomicCount1.sum());
    }
}

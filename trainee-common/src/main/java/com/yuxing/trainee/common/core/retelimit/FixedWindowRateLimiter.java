package com.yuxing.trainee.common.core.retelimit;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 固定窗口计数器限流
 *
 * 实现原理：该算法在单位时间内，累加访问次数，当达到阈值时，触发限流策略。在下一个周期开始时，进行清零，重新计数。
 * 缺点：限流不均匀 -> 简单计数实现简单，但存在一个致命的问题，即临界问题，例如，1分钟内限制请求总数为100的场景下，前一分钟的最后1s进入100个请求，
 * 之后下一个一分钟的第一个1s进入100个请求。尽管是在不同的一分钟区间内发生的，但事实上却是在不到一分钟的时间内，进入了200个请求，因此计数器限流失效
 *
 * @author yuxing
 */
@Slf4j
public class FixedWindowRateLimiter {

    public FixedWindowRateLimiter(int limitCount, long periodForSecond) {
        this(limitCount, periodForSecond, TimeUnit.SECONDS);
    }

    public FixedWindowRateLimiter(int limitCount, long windowSize, TimeUnit timeUnit) {
        this.limitCount = limitCount;
        this.counter = new AtomicInteger(0);
        this.timeUnit = timeUnit;
        this.windowSize = timeUnit.toSeconds(windowSize);
        new Thread(new CounterResetThread()).start();
    }

    /**
     * 限流计数器
     */
    private final AtomicInteger counter;

    /**
     * 单位时间内的限制访问次数
     */
    private final int limitCount;

    /**
     * 限流器时间窗口大小：e.g. 每分钟、每秒等
     */
    private final long windowSize;

    /**
     * 周期时长单位
     */
    private final TimeUnit timeUnit;

    /**
     * 是否已达到限流阈值
     */
    private volatile boolean limited;

    /**
     * 获取访问许可
     *
     * @return 返回true说明允许访问，false说明已到达限流阈值
     */
    public boolean acquire() {
        // 当前已到达限流阈值，则直接返回false
        if (this.limited) {
            log.debug("单位时间内，累加访问次数已达到阈值，限流");
            return false;
        }

        int currentLimit = this.counter.get();
        if (this.limitCount == (currentLimit + 1)) {
            synchronized (this) {
                if (this.limitCount > currentLimit) {
                    this.counter.incrementAndGet();
                }

                this.limited = true;
            }
        } else {
            this.counter.incrementAndGet();
        }
        return true;
    }

    /**
     * 重置计数器线程
     */
    class CounterResetThread implements Runnable {

        @Override
        @SuppressWarnings("InfiniteLoopStatement")
        public void run() {
            while (true) {
                try {
                    timeUnit.sleep(windowSize);
                    // 计数器清零
                    counter.set(0);
                    limited = false;
                    log.debug("当前限流计数周期已结束，重置计数器");
                } catch (InterruptedException e) {
                    log.error("重置FixedWindowCounterLimit计数器失败", e);
                }
            }
        }
    }

}

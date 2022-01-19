package com.yuxing.trainee.core.retelimit;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 滑动窗口计数限流
 *
 * @author yuxing
 */
@Slf4j
public class SlidingWindowRateLimiter {

    private final Object lock = new Object();

    /**
     * 限流器时间窗口大小：e.g. 每分钟、每秒等
     */
    private final long windowSize;

    /**
     * 周期时长单位
     */
    private final TimeUnit timeUnit;

    /**
     * 单位时间内的限制访问次数
     */
    private final int limitCount;

    /**
     * 当前计数窗格
     */
    private volatile PaneNode writePos;

    public SlidingWindowRateLimiter(long windowSize, TimeUnit timeUnit, int limitCount, int gridSize) {
        this.timeUnit = timeUnit;
        this.windowSize = this.timeUnit.toSeconds(windowSize);
        this.limitCount = limitCount;
        long startTime = System.currentTimeMillis();
        int threshold = limitCount / gridSize;
        int period = (int) windowSize / gridSize;
        PaneNode checkpoint = this.writePos = new PaneNode(0, threshold, startTime);
        PaneNode head = null;
        PaneNode tail = null;
        for (long i = 0, time = startTime + period; i < gridSize - 1; i++, time += period) {
            PaneNode node = new PaneNode(((int) i) + 1, threshold, time);
            if (head == null) {
                head = node;
                tail = node;
                continue;
            }
            PaneNode temp = tail;
            tail = node;
            temp.next = tail;
        }
        if  (head != null) {
            checkpoint.next = head;
            tail.next = checkpoint;
        }

        new Thread(new CounterResetThread(threshold)).start();
    }

    public SlidingWindowRateLimiter(long windowSize, int limitCount, int gridSize) {
        this(windowSize, TimeUnit.SECONDS, limitCount, gridSize);
    }

    public boolean acquire() {
        // 当前已到达限流阈值，则直接返回false
        if (this.writePos.limited) {
            log.debug("第 {} 窗格，当前访问计数：{}，累计访问：{}, 单位时间内，累加访问次数已达到阈值，限流", this.writePos.index, this.writePos.counter.get(), this.getTotalCounter());
            return false;
        }

        int currentLimit = this.writePos.counter.get();
        if (this.writePos.threshold == (currentLimit + 1)) {
            synchronized (this) {
                if (this.limitCount > currentLimit) {
                    this.writePos.counter.incrementAndGet();
                }

                this.writePos.limited = true;
            }
        } else {
            this.writePos.counter.incrementAndGet();
        }
        log.debug("第{}窗格，限制访问次数: {}, 当前访问计数：{}，时间周期内累计访问次数：{}", this.writePos.index, this.writePos.threshold, this.writePos.counter.get(), this.getTotalCounter());
        return true;
    }

    public int getTotalCounter() {
        PaneNode temp = this.writePos;
        int count = 0;
        do {
            count += temp.counter.get();
        }
        while ((temp = temp.next) != this.writePos);
        return count;
    }

    /**
     * 窗格
     */
    static class PaneNode {

        /**
         * 窗格索引
         */
        private final int index;

        /**
         * 单个窗格阈值
         */
        private final int threshold;

        /**
         * 起始时间
         */
        private long startTime;

        /**
         * 计数器
         */
        private final AtomicInteger counter;

        /**
         * 是否已达到限流阈值
         */
        private volatile boolean limited;

        /**
         * 下个节点
         */
        private PaneNode next;

        public PaneNode(int index, int threshold, long startTime) {
            this.index = index;
            this.threshold = threshold;
            this.startTime = startTime;
            this.counter = new AtomicInteger(0);
        }
    }

    /**
     * 重置计数器线程
     */
    class CounterResetThread implements Runnable {

        private final long period;

        public CounterResetThread(long period) {
            this.period = period;
        }

        @Override
        @SuppressWarnings("InfiniteLoopStatement")
        public void run() {
            while (true) {
                try {
                    timeUnit.sleep(this.period);
                    synchronized (lock) {
                        long currentTime = System.currentTimeMillis();
                        PaneNode nextPaneNode = writePos.next;
                        long startTime = nextPaneNode.startTime;
                        if (currentTime - startTime >= windowSize) {
                            nextPaneNode.startTime = currentTime;
                            nextPaneNode.counter.set(0);
                            nextPaneNode.limited = false;
                            log.debug("重置计数窗格：{}", nextPaneNode.index);
                        }
                        writePos = writePos.next;
                    }
                } catch (InterruptedException e) {
                    log.error("重置SlidingWindowCounterLimit计数器失败", e);
                }
            }
        }
    }
}

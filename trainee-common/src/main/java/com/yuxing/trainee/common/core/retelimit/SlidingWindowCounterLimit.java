package com.yuxing.trainee.common.core.retelimit;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 滑动窗口计数限流
 *
 * @author yuxing
 */
@Slf4j
public class SlidingWindowCounterLimit {

    public SlidingWindowCounterLimit(int limitCount, long period, int periodPartitionNumber) {
        this(limitCount, period, TimeUnit.SECONDS, periodPartitionNumber);
    }

    public SlidingWindowCounterLimit(int limitCount, long period, TimeUnit timeUnit, int periodPartitionNumber) {
        this.limitCount = limitCount;
        this.partitionLimit = this.limitCount / periodPartitionNumber;
        this.timeUnit = timeUnit;
        this.period = timeUnit.toSeconds(period);
        this.periodPartitionNumber = periodPartitionNumber;
        this.perPartitionTime = this.period / this.periodPartitionNumber;
        this.byStages = new AtomicInteger[this.periodPartitionNumber];
        for (int i = 0; i < this.periodPartitionNumber; i++) {
            this.byStages[i] = new AtomicInteger(0);
        }
        this.startTime = LocalDateTime.now();
        this.currentIndex = this.getCurrentIndex();
        this.totalAccess = new AtomicInteger(0);
        new Thread(new CounterResetThread()).start();
    }


    /**
     * 限制单位时间内的访问次数
     */
    private final int limitCount;

    /**
     * 分区限制请求数
     */
    private final int partitionLimit;

    /**
     * 单位时间的周期长度
     */
    private final long period;

    /**
     * 单位时间的分区数
     */
    private final int periodPartitionNumber;

    /**
     * 周期时长单位
     */
    private final TimeUnit timeUnit;

    /**
     * 分期
     */
    private final AtomicInteger[] byStages;

    /**
     * 开始时间
     */
    private final LocalDateTime startTime;

    /**
     * 每个区间的时间周期
     */
    private final long perPartitionTime;

    /**
     * 当前分区索引
     */
    private volatile int currentIndex;

    /**
     * 总访问量
     */
    private final AtomicInteger totalAccess;

    public boolean acquire() {
        // 总访问数已达到阈值，则进行限流
        int currentAccess = this.getTotalAccessCount();
        if (this.limitCount == currentAccess) {
            return false;
        }
        AtomicInteger byStage = this.byStages[this.currentIndex];
        int currentStageAccess = byStage.get();
        // 当前分区访问数已到达分区阈值，则进行限流
        if (this.partitionLimit == currentStageAccess) {
            return false;
        }

        byStage.incrementAndGet();
        return true;
    }

    /**
     * 获取当前分区索引
     *
     * @return 当前分区索引
     */
    private int getCurrentIndex() {
        LocalDateTime nowTime = LocalDateTime.now();
        long subtraction = this.getTimeSubtraction(nowTime);

        return (int) ((subtraction % this.period) / this.perPartitionTime);
    }

    private long getTimeSubtraction(LocalDateTime nowTime) {
        long subtractionMillis = Duration.between(startTime, nowTime).toMillis();
        return TimeUnit.MILLISECONDS.toSeconds(subtractionMillis);
    }

    /**
     * 获取总访问量
     * @return 总访问量
     */
    private int getTotalAccessCount() {
        return this.totalAccess.get() + byStages[currentIndex].get();
    }

    class CounterResetThread implements Runnable {

        @Override
        @SuppressWarnings("InfiniteLoopStatement")
        public void run() {
            while (true) {
                try {
                    // 睡眠1毫秒
                    timeUnit.sleep(1);
                    int newCurrentIndex = getCurrentIndex();
                    if (newCurrentIndex == currentIndex) {
                        continue;
                    }
                    int index2Reset = currentIndex - 1;
                    if (index2Reset >= 0) {
                        AtomicInteger resetStage = byStages[index2Reset];
                        totalAccess.addAndGet(resetStage.get());
                        log.info("当前分区：{}, 访问量：{}, 重置分区：{}, 访问量：{}, 总访问量：{}", currentIndex, byStages[currentIndex].get(), index2Reset, resetStage.get(), getTotalAccessCount());
                        resetStage.set(0);
                    }

                    // 若当前周期已结束则重置全部计数器
                    if (currentIndex == periodPartitionNumber) {
                        totalAccess.set(0);
                    }
                    currentIndex = newCurrentIndex;
                } catch (InterruptedException e) {
                    log.error("重置SlidingWindowCounterLimit计数器失败", e);
                }
            }
        }
    }
}

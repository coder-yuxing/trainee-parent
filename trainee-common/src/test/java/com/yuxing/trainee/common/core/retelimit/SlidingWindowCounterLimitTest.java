package com.yuxing.trainee.common.core.retelimit;

import org.junit.Test;

public class SlidingWindowCounterLimitTest {

    @Test
    public void acquire() throws Exception {
        // 30s内限制最大60次访问
        SlidingWindowCounterLimit counterLimit = new SlidingWindowCounterLimit(10, 20, 5);

        for (int i = 0; i < 1000; i++) {
            if (i % 15 == 0) {
                Thread.sleep(1000);
            }
            new Thread(new TestThread(counterLimit)).start();
        }

        Thread.sleep(Integer.MAX_VALUE);
    }

    static class TestThread implements Runnable {

        public TestThread(SlidingWindowCounterLimit counterLimit) {
            this.counterLimit = counterLimit;
        }

        private final SlidingWindowCounterLimit counterLimit;

        @Override
        public void run() {
            boolean acquire = counterLimit.acquire();
            if (acquire) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
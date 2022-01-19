package com.yuxing.trainee.core.retelimit;

import cn.hutool.core.date.DateUtil;
import org.junit.Test;

import java.util.Date;

public class FixedWindowCounterLimitTest {

    @Test
    public void acquire() throws Exception {
        // 1s内限制最大10次访问
        FixedWindowRateLimiter counterLimit = new FixedWindowRateLimiter(10, 1);

        for (int i = 0; i < 30; i++) {

            if (i == 15) {
                Thread.sleep(1000);
            }
            new Thread(new TestThread(counterLimit, i, new Date())).start();
        }

        Thread.sleep(Integer.MAX_VALUE);
    }

    static class TestThread implements Runnable {

        public TestThread(FixedWindowRateLimiter counterLimit, int serialNumber, Date accessTime) {
            this.counterLimit = counterLimit;
            this.serialNumber = serialNumber;
            this.accessTime = accessTime;
        }

        private final FixedWindowRateLimiter counterLimit;

        /**
         * 访问序号
         */
        private final int serialNumber;

        /**
         * 访问时间
         */
        private final Date accessTime;

        @Override
        public void run() {
            boolean acquire = counterLimit.acquire();
            if (acquire) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.err.println("第 " + serialNumber + "次访问， 访问时间：" + DateUtil.format(accessTime, "yyy/MM/dd HH:mm:ss") + ", 访问成功");
            } else {
                System.err.println("第 " + serialNumber + "次访问， 访问时间：" + DateUtil.format(accessTime, "yyy/MM/dd HH:mm:ss") + ", 访问失败");
            }
        }
    }

}

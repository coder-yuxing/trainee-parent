package com.yuxing.trainee.core.concurrent;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yuxing
 * @since 2021/12/21
 */
public class CustomThreadPoolExecutorTest {

    @Test
    public void executeTest() throws Exception {

        Executor threadPool = new CustomThreadPoolExecutor("test", 5, 10, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<>(15), 0);
        AtomicInteger num = new AtomicInteger(0);

        for (int i = 0; i < 100; i++) {
            threadPool.execute(()->{
                try {
                    Thread.sleep(1000);
                    System.out.println("running: " + System.currentTimeMillis() + ": " + num.incrementAndGet());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(Integer.MAX_VALUE);


    }
}
package com.yuxing.trainee.common.concurrent;

import org.junit.Assert;
import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 创建线程的几种方式
 *
 * @author yuxing
 * @since 2021/12/21
 */
public class ThreadTest {

    @Test
    public void createThreadTest() throws Exception {
        // 继承 Thread 并重写 run方法
        new CustomThread().start();

        // 实现 Runnable接口
        new Thread(new CustomRunnable()).start();

        // 匿名内部类
        new Thread(() -> {
            System.err.println("do something");
        }).start();

        // 实现 Callable 接口，可获取线程执行结果
        FutureTask<String> futureTask = new FutureTask<>(new CustomCallable());
        new Thread(futureTask).start();

        Assert.assertEquals("success", futureTask.get());

        // 定时器
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.err.println("do something");
            }
        }, 0, 1000);

        // 线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(() -> System.err.println("do something"));
    }



    static class CustomCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.err.println("do something");
            return "success";
        }
    }

    static class CustomRunnable implements Runnable {

        @Override
        public void run() {
            System.err.println("do something");
        }
    }

    static class CustomThread extends Thread {

        @Override
        public void run() {
            System.err.println("do something");
        }
    }
}

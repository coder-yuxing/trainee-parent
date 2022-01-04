package com.yuxing.trainee.example.lock;

import lombok.Getter;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * java中线程的阻塞与唤醒
 *
 * @author yuxing
 * @since 2022/1/4
 */
public class ThreadBlockingAndWakeupTest {


    /**
     * Object: wait、notify、notifyAll
     */
    @Test
    public void waitAndNotifyAllTest() throws Exception {
        // wait、notify、notifyAll 方法必须与synchronized关键字配合使用，单独调用会抛出IllegalMonitorStateException异常
        // Object o = new Object();
        // o.wait();

        final int capacity = 10;
        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(capacity);
        Producer producer = new Producer(blockingQueue, capacity);
        Consumer consumer = new Consumer(blockingQueue);
        producer.start();
        consumer.start();

        Thread.sleep(Integer.MAX_VALUE);
    }

    static class Producer extends Thread {

        public Producer(BlockingQueue<String> blockingQueue, int capacity) {
            this.blockingQueue = blockingQueue;
            this.capacity = capacity;
        }

        private final BlockingQueue<String> blockingQueue;
        private final int capacity;
        private final Random random = new Random();
        @Override
        public void run() {
            while (true) {
                try {
                    synchronized (this.blockingQueue) {
                        while (this.blockingQueue.size() >= capacity) {
                            // 队列空间不足时，暂停生产线程
                            this.blockingQueue.wait();

                        }
                        String s = this.random.nextInt(1000) + "";
                        System.err.println("生产者生产：" + s);
                        this.blockingQueue.put(s);
                        this.blockingQueue.notifyAll();
                        System.err.println("唤醒阻塞线程-producer");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer extends Thread {

        private final BlockingQueue<String> blockingQueue;

        public Consumer(BlockingQueue<String> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    synchronized (this.blockingQueue) {
                        while (this.blockingQueue.isEmpty()) {
                            this.blockingQueue.wait();
                        }

                        System.err.println("消费者消费：" + this.blockingQueue.take());
                        this.blockingQueue.notifyAll();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


    /**
     * Thread.suspend() & Thread.resume() 方法：
     * - suspend 用于将一个线程挂起(暂停) -> 注意：该过程并不会释放其占用的锁资源
     * - resume 用于将一个挂起的线程唤起继续执行
     */
    @Test
    public void suspendAndResumeTest() throws Exception {
        Object lock = new Object();
        CustomThread producer = new CustomThread("producer", lock);
        CustomThread consumer = new CustomThread("consumer", lock);

        producer.start();
        // 此处暂停2s，以便让 producer先运行
        Thread.sleep(2000);
        consumer.start();

        producer.resume();
        consumer.resume();
        producer.join();
        consumer.join();
        System.out.println("done");

        // 执行结果：
        // in producer
        // producer is consume.
        // in consumer
        // 程序无法终止。consumer 线程resume在suspend之前被调用，导致其处于阻塞状态...
        // 另一个需要注意的是，尽管consumer线程被阻塞，但其状态为 Runnable

    }

    @Getter
    static class CustomThread extends Thread {

        public CustomThread(String name, Object lock) {
            super(name);
            this.lock = lock;
        }

        private final Object lock;

        @Override
        public void run() {
            synchronized (this.lock) {
                System.err.println("in " + this.getName());
                // 阻塞线程
                this.suspend();
                System.err.println(this.getName() + " is consume.");
            }
        }
    }
}

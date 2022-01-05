package com.yuxing.trainee.example.lock;

import lombok.Getter;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * java中线程的阻塞与唤醒
 *
 * @author yuxing
 * @since 2022/1/4
 */
public class ThreadBlockingAndWakeupTest {

    /**
     * Condition: await & signal
     * java在语言层面提供了 Lock 与 Condition: await & signal 方法配合完成线程间等待/通知的机制
     * 其底层基于 LockSupport#park & LockSupport#unpark
     */
    @Test
    public void conditionAwaitAndSignalTest() throws Exception {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        ConditionAwait conditionAwait = new ConditionAwait(lock, condition);
        ConditionSignal conditionSignal = new ConditionSignal(lock, condition);
        // new Thread(conditionAwait).start();
        // // 阻塞2s,以便让conditionAwait先执行
        // Thread.sleep(2000);
        // new Thread(conditionSignal).start();

        // 让conditionSignal运行(signal先调用)
        // signal先调用后调用await会导致线程阻塞无法被唤醒
        new Thread(conditionSignal).start();
        Thread.sleep(2000);
        new Thread(conditionAwait).start();
    }

    static class ConditionSignal implements Runnable {
        private final Lock lock;
        private final Condition condition;

        public ConditionSignal(Lock lock, Condition condition) {
            this.lock = lock;
            this.condition = condition;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                System.err.println("ConditionSignal start...");
                System.err.println("ConditionSignal 唤醒其他线程...");
                condition.signalAll();
                System.err.println("ConditionSignal 继续运行...");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class ConditionAwait implements Runnable {

        private final Lock lock;
        private final Condition condition;

        public ConditionAwait(Lock lock, Condition condition) {
            this.lock = lock;
            this.condition = condition;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                System.err.println("ConditionAwait start...");
                System.err.println("ConditionAwait await...");
                condition.await();
                System.err.println("ConditionAwait 继续运行...");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }




    /**
     * Object: wait、notify、notifyAll
     * wait、notify、notifyAll 方法是java底层级别提供的与对象监视器配合完成线程间等待/通知机制
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

    /**
     * Object: wait、notify、notifyAll
     */
    @Test
    public void objectWaitAndNotifyTest() throws Exception {
        TaskQueue taskQueue = new TaskQueue();
        List<Thread> taskThreads = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    try {
                        String task = taskQueue.getTask();
                        System.err.println("execute task: " + task);
                    } catch (Exception e) {
                        return;
                    }
                }
            });
            thread.start();
            taskThreads.add(thread);
        }

        Thread addTaskThread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                String task = "task-" + Math.random();
                System.err.println("add task: " + task);
                taskQueue.addTask(task);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        addTaskThread.start();
        // 等待 addTaskThread 线程执行完毕，再继续执行main Thread后续代码
        addTaskThread.join();

        Thread.sleep(100);

        for (Thread t : taskThreads) {
            t.interrupt();
        }
    }


    static class TaskQueue {

        private Queue<String> taskQueue = new LinkedList<>();

        public synchronized void addTask(String task) {
            taskQueue.add(task);
            // 任务队列中添加任务成功时，唤醒获取任务的全部挂起线程
            this.notifyAll();
        }

        public synchronized String getTask() throws Exception {
            while (this.taskQueue.isEmpty()) {
                // 若当前任务队列为空，则当前获取任务线程挂起等待
                // wait() 方法必须在当前获取的锁对象上调用
                // 在线程 wait() 等待时，会释放 this锁
                this.wait();
            }
            return taskQueue.remove();
        }

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


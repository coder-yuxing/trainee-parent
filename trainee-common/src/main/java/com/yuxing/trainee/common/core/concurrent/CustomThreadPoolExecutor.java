package com.yuxing.trainee.common.core.concurrent;


import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义线程池
 *
 *  {@link Executor} 实现了任务发布于任务执行的解耦，任务发布者只需关心业务逻辑的实现，隐藏了任务的执行与线程管理的细节
 *
 *  采用生产者-消费者模型完成任务的提交 & 任务的消费
 *  - {@link CustomThreadPoolExecutor#execute(Runnable)} 完成生产者角色
 *  - {@link Worker} 完成消费者角色
 *
 *
 * @author yuxing
 * @since 2021/12/21
 */
@Slf4j
public class CustomThreadPoolExecutor implements Executor {

    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 线程名称前缀
     */
    private final String threadNamePrefix;

    /**
     * 核心线程数
     */
    private final int corePoolSize;

    /**
     * 最大线程数
     */
    private final int maxPoolSize;

    /**
     * 线程存活时间
     */
    private final long keepLiveTime;

    /**
     * 阻塞队列
     *
     * 阻塞队列用于平衡任务发布于任务处理二者间的效率差异
     */
    private final BlockingQueue<Runnable> taskQueue;

    /**
     * 任务抛弃策略
     */
    private final int abortPolicy;

    /**
     * 工作线程
     */
    private final Set<Worker> workers;

    /**
     * 工作线程数量
     */
    private final AtomicInteger workerCount = new AtomicInteger(0);

    public CustomThreadPoolExecutor(int corePoolSize, int maxPoolSize, long keepLiveTime, TimeUnit unit, BlockingQueue<Runnable> taskQueue, int abortPolicy) {
        this("custom-thread-", corePoolSize, maxPoolSize, keepLiveTime, unit, taskQueue, abortPolicy);
    }

    public CustomThreadPoolExecutor(String threadNamePrefix, int corePoolSize, int maxPoolSize, long keepLiveTime, TimeUnit unit, BlockingQueue<Runnable> taskQueue, int abortPolicy) {
        this.threadNamePrefix = threadNamePrefix;
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.taskQueue = taskQueue;
        this.abortPolicy = abortPolicy;
        this.keepLiveTime = unit.toMillis(keepLiveTime);
        this.workers = new HashSet<>(this.maxPoolSize);
    }

    @Override
    public void execute(Runnable command) {

        if (command == null) {
            throw new NullPointerException();
        }


        // 1.尝试创建核心线程执行任务
        int runningThreadNum = workerCount.get();
        if (runningThreadNum < this.corePoolSize
                && this.addWorker(command, true)) {
            // 若核心线程创建成功，则直接返回
            return;
        }

        // 2.创建核心线程执行任务失败，则尝试将任务加入阻塞队列
        if (this.taskQueue.offer(command)) {
            return;
        }

        // 3.阻塞队列已满，则尝试创建非核心线程执行任务
        runningThreadNum = workerCount.get();
        if (runningThreadNum < this.maxPoolSize
                && this.addWorker(command, false)) {
            return;
        }

        // 4.若非核心线程已创建完毕，则根据任务抛弃策略放弃任务
        this.abortTask(command);
    }


    private boolean addWorker(Runnable firstTask, boolean core) {
        int maxNum = core ? this.corePoolSize : this.maxPoolSize;
        int runningThreadNum = this.workerCount.get();
        if (runningThreadNum < maxNum) {
            Worker worker = new Worker(firstTask);
            final Thread thread = worker.thread;
            if (thread != null) {
                lock.lock();
                try {
                    this.workers.add(worker);
                    int workerNum = this.workerCount.incrementAndGet();
                    thread.setName(this.threadNamePrefix + workerNum);
                    thread.start();
                    return true;
                } finally {
                    lock.unlock();
                }
            }
        }
        return false;
    }

    private void abortTask(Runnable command) {
        // do something
        System.err.println("abortTask");
    }

    class Worker implements Runnable {

        public Worker(Runnable firstTask) {
            this.firstTask = firstTask;
            this.thread = new Thread(this);
        }

        private final Runnable firstTask;

        private final Thread thread;

        @Override
        public void run() {
            try {
                // 1.若存在firstTask,执行firstTask
                // 2.不存在firstTask 或已执行完毕，则尝试从工作队列中获取任务继续执行
                Runnable task = firstTask;
                while (task != null || (task = getTask()) != null) {
                    try {
                        log.debug("{} running", this.thread.getName());
                        task.run();
                    } finally {
                        task = null;
                    }
                }
            } finally {
                lock.lock();
                try {
                    // 未获取到任务，则从工作线程集合中删除当前线程
                    log.debug("指定时间内未获取到任务，线程{}执行完毕，即将释放资源", this.thread.getName());
                    workers.remove(this);
                    workerCount.decrementAndGet();
                } finally {
                    lock.unlock();
                }
            }
        }

        private Runnable getTask() {
            try {
                // poll 方法取走BlockingQueue中排首位的对象，若不能立即获取，则可等待 keepLiveTime 规定的时间，超时时返回null
                // 通过该操作控制了线程的存活时间
                return taskQueue.poll(keepLiveTime, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
               // e.printStackTrace();
               return null;
            }
        }
    }
}

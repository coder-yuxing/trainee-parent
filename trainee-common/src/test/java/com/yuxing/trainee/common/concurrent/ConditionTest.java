package com.yuxing.trainee.common.concurrent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author yuxing
 * @since 2021/12/21
 */
public class ConditionTest {


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
}

package com.yuxing.trainee.example.interview.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可见性相关
 *
 * @author yuxing
 * @since 2021/12/27
 */
public class VisibilityTest {

    public static void main(String[] args) {
        volatileVisibilityTest();
    }

    /**
     * 该方法中，假如线程t1先于线程t2执行，那么t1对于value的修改是否对t2可见(即t2能否看到value的正确结果)？为什么？
     *
     * ① t1的修改对t2是可见的
     * ② 保证可见性的原理：此处利用了volatile的 happen-before规则。Java提供的 ReentrantLock内部基于AQS实现同步，
     *    AQS内部持有一个volatile的成员变量 state，获取锁时会读写state的值；解锁时也会读写state的值。也就是说，在下面代码中，
     *    在执行 value++之前，程序先读写了一次volatile变量state,在执行value++之后，又读写了一次volatile变量state。故，
     *    根据volatile相关的happen-before规则：
     *      - 顺序性规则：对于线程t1, value++ happen-before 释放锁的操作unlock()
     *      - volatile变量规则：由于state=1 会先读取state,所以线程t1的unlock()操作 happen-before 线程t2 的lock()操作
     *      - 传递性规则：线程 t1 的value++ happen-before 线程t2的lock()操作
     *    因此，后续线程t2能够看到value的正确结果
     *
     */
    private static void volatileVisibilityTest() {
        Test1 test1 = new Test1();

        Thread t1 = new Thread(test1::add);
        Thread t2 = new Thread(test1::add);

        t1.start();
        t2.start();
    }

    static class Test1 {
        private final Lock lock = new ReentrantLock();

        int value;

        public void add() {
            lock.lock();
            try {
                value++;
            } finally {
                lock.unlock();
            }
        }
    }
}

package com.yuxing.trainee.example.concurrent.lock;

/**
 * @author yuxing
 * @since 2021/12/30
 */
public class SynchronizedTest {

    public static void main(String[] args) {
        // 两个线程竞争同一把锁—— thisLockTest 对象，因此必须等待获取锁的线程释放锁后另一个线程才能获取锁
        // ThisLockTest thisLockTest = new ThisLockTest();
        // Thread t1 = new Thread(thisLockTest);
        // Thread t2 = new Thread(thisLockTest);
        //
        // t1.start();
        // t2.start();

        // 存在两把锁时，当线程1释放lock1后，线程2无需等待即可执行，同时线程1获取lock2执行
        // TwoLockTest twoLockTest = new TwoLockTest();
        // Thread t3 = new Thread(twoLockTest);
        // Thread t4 = new Thread(twoLockTest);
        // t3.start();
        // t4.start();

        // synchronized 锁对象为 Class对象
        // 两个线程竞争的是同一把锁，因此会一次执行
        new Thread(new ClassObjLock()).start();
        new Thread(new ClassObjLock()).start();
    }

    static class ThisLockTest implements Runnable {

        @Override
        public void run() {
            synchronized (this) {
                System.out.println(Thread.currentThread().getName() +  " 获取锁，开始执行...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 执行结束");
            }
        }
    }

    static class TwoLockTest implements Runnable {

        Object lock1 = new Object();
        Object lock2 = new Object();

        @Override
        public void run() {
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() +  " 获取锁，开始执行...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 执行结束");
            }

            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() +  " 获取锁，开始执行...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 执行结束");
            }
        }
    }

    static class ClassObjLock implements Runnable {

        @Override
        public void run() {
            // 所有线程需要的锁都是同一把
            synchronized(ClassObjLock.class){
                System.out.println(Thread.currentThread().getName() +  " 获取锁，开始执行...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 执行结束");
            }
        }

    }
}

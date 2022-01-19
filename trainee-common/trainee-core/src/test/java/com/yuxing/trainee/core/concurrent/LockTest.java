package com.yuxing.trainee.core.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock是独占锁
 * Lock锁的使用，把锁和要用锁同步的代码放在一起，这里就是放在Printer类中了 
   *     获取到锁后，最后要在finally代码块中手动释放锁
 */
public class LockTest {

    public static void main(String[] args) {

        Printer printer = new LockTest().new Printer();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    printer.print("zhangsan33953");
                }

            };
        }.start();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    printer.print("lisi27965");
                }
            };
        }.start();
    }

    class Printer {

        private Lock lock = new ReentrantLock();//默认是非公平锁

        public void print(String name) {
            lock.lock(); // 获取锁 ， 获取不到会阻塞
            try {
                
                int len = name.length();
                for (int i = 0; i < len; i++) {
                    System.out.print(name.charAt(i));
                }
                System.out.println();
                
            } finally {
                lock.unlock(); // 释放锁
            }
        }
    }

}
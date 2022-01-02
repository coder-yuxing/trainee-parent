package com.yuxing.trainee.example.concurrent.lock;

/**
 * 同步方法
 *
 * @author yuxing
 * @since 2022/1/2
 */
public class SyncMethodTest {

    private int i;

    public synchronized void add() {
        i++;
    }
}

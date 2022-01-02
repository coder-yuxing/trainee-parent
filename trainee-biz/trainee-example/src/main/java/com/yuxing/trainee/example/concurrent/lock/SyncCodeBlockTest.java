package com.yuxing.trainee.example.concurrent.lock;

/**
 * 同步代码块
 *
 * @author yuxing
 * @since 2022/1/2
 */
public class SyncCodeBlockTest {

    private int i;

    public void test() {
        synchronized (this) {
            i++;
        }
    }
}

package com.yuxing.trainee.example.concurrent.designpattern.balking;

import java.util.concurrent.TimeUnit;

/**
 * @author yuxing
 * @since 2021/12/24
 */
public class ServerThread implements Runnable {

    private final Data data;

    public ServerThread(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
                // 定时保存文件内容
                data.save();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

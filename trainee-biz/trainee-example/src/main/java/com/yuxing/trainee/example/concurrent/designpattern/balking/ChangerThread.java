package com.yuxing.trainee.example.concurrent.designpattern.balking;

import java.util.Random;

/**
 * @author yuxing
 * @since 2021/12/24
 */
public class ChangerThread implements Runnable {

    private static final Random random = new Random();

    private final Data data;

    public ChangerThread(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true ; i++) {
                data.change("No." + i);
                Thread.sleep(random.nextInt(1000));
                data.save();
            }
        } catch (Exception ignore) {

        }
    }
}

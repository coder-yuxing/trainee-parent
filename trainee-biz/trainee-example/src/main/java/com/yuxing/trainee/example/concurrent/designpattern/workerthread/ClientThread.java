package com.yuxing.trainee.example.concurrent.designpattern.workerthread;

import java.util.Random;

/**
 * 客户端线程，模拟用户用于发布任务(工作)
 *
 * @author yuxing
 * @since 2022/1/11
 */
public class ClientThread extends Thread {

    private static final Random random = new Random();

    private final Channel channel;

    public ClientThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                Request request = new Request(this.getName(), i);
                channel.putRequest(request);
                Thread.sleep(random.nextInt(1000));
            }
        } catch (Exception e) {
            // do something
        }
    }
}

package com.yuxing.trainee.example.concurrent.designpattern.workerthread;

/**
 * 启动类
 *
 * @author yuxing
 * @since 2022/1/11
 */
public class Main {

    public static void main(String[] args) {
        Channel channel = new Channel(5);
        channel.startWorkers();
        new ClientThread("Alice", channel).start();
        new ClientThread("Bobby", channel).start();
        new ClientThread("Chris", channel).start();
    }
}

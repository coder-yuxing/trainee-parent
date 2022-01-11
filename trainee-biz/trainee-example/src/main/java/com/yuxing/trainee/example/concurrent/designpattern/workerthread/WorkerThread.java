package com.yuxing.trainee.example.concurrent.designpattern.workerthread;

/**
 * 工人线程，负责获取并执行任务，在没有任务时进行等待
 *
 * @author yuxing
 * @since 2022/1/11
 */
public class WorkerThread extends Thread {


    private final Channel channel;

    public WorkerThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {
            Request request = channel.takeRequest();
            request.execute();
        }
    }
}

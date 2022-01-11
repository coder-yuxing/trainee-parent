package com.yuxing.trainee.example.concurrent.designpattern.workerthread;

/**
 * 工作池，负责接收/存储用户发布的工作
 *
 * @author yuxing
 * @since 2022/1/11
 */
public class Channel {

    private static final int MAX_REQUEST = 100;

    /**
     * 工作队列
     */
    private final Request[] requestQueue;

    /**
     * 下次putRequest的位置
     */
    private int tail;

    /**
     * 下次takeRequest的位置
     */
    private int head;

    /**
     * request的数量
     */
    private int count;

    /**
     * 工人线程集合
     */
    private final WorkerThread[] threadPool;

    public Channel(int threads) {
        this.requestQueue = new Request[MAX_REQUEST];
        this.tail = 0;
        this.head = 0;
        this.count = 0;
        this.threadPool = new WorkerThread[threads];
        for (int i = 0; i < this.threadPool.length; i++) {
            threadPool[i] = new WorkerThread("Worker-" + i, this);
        }
    }

    /**
     * 启动工人线程
     */
    public void startWorkers() {
        for (WorkerThread workerThread : this.threadPool) {
            workerThread.start();
        }
    }

    public synchronized void putRequest(Request request) {
        while (count >= this.requestQueue.length) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                // do something
            }
        }
        this.requestQueue[tail] = request;
        this.tail = (this.tail + 1) % this.requestQueue.length;
        this.count++;
        this.notifyAll();
    }

    public synchronized Request takeRequest() {
        while (count <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                // do something
            }
        }
        Request request = this.requestQueue[head];
        this.head = (this.head + 1) % this.requestQueue.length;
        this.count--;
        this.notifyAll();
        return request;
    }
}

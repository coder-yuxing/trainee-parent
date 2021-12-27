package com.yuxing.trainee.example.concurrent.designpattern.producerconsumer;

/**
 * @author yuxing
 * @since 2021/12/27
 */
public class Table {

    private final String[] buffer;
    private int head;
    private int tail;
    private int count;

    public Table(int count) {
        buffer = new String[count];
    }

    public synchronized void put(String bread) throws InterruptedException {
        while (count >= buffer.length) {
            this.wait();
        }

        this.buffer[this.tail] = bread;
        this.tail = (this.tail + 1) % this.buffer.length;
        this.count++;
        this.notifyAll();
    }

    public synchronized String take() throws InterruptedException {
        while (count == 0) {
            this.wait();
        }
        String result = this.buffer[this.head];
        this.head = (this.head + 1) % this.buffer.length;
        this.count--;
        this.notifyAll();
        return result;
    }
}

package com.yuxing.trainee.example.concurrent.designpattern.guardedsuspension.demo1;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author yuxing
 * @since 2021/12/23
 */
public class RequestQueue {

    private Queue<Request> queue = new LinkedList<>();

    public synchronized void putRequest(Request request) {
        queue.offer(request);
        notifyAll();
    }

    public synchronized Request getRequest() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return queue.remove();
    }
}

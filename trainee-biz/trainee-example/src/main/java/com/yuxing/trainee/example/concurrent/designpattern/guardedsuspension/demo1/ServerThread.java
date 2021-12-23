package com.yuxing.trainee.example.concurrent.designpattern.guardedsuspension.demo1;

import lombok.AllArgsConstructor;

/**
 * @author yuxing
 * @since 2021/12/23
 */
@AllArgsConstructor
public class ServerThread implements Runnable {

    private final RequestQueue requestQueue;

    private final String serverName;

    @Override
    public void run() {
        Request request;
        while ((request = requestQueue.getRequest()) != null) {
            System.err.println(serverName + " reply: hello " + request.getContent() + " " + request.getId());
        }
    }
}

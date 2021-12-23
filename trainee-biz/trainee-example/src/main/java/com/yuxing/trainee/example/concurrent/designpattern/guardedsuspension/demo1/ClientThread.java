package com.yuxing.trainee.example.concurrent.designpattern.guardedsuspension.demo1;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yuxing
 * @since 2021/12/23
 */
@Data
@AllArgsConstructor
public class ClientThread implements Runnable {

    private final RequestQueue requestQueue;

    private final String clientName;


    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.err.println(clientName + " call: hello " + i);
            requestQueue.putRequest(new Request(i + "", clientName));
        }
    }
}

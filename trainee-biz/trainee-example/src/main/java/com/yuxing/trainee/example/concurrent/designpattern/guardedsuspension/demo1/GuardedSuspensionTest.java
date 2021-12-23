package com.yuxing.trainee.example.concurrent.designpattern.guardedsuspension.demo1;


/**
 * 保护性暂停模式使用案例
 *
 * @author yuxing
 * @since 2021/12/23
 */
public class GuardedSuspensionTest {

    public static void main(String[] args) {
        RequestQueue requestQueue = new RequestQueue();
        new Thread(new ServerThread(requestQueue, "server")).start();
        new Thread(new ClientThread(requestQueue, "client")).start();
    }


}

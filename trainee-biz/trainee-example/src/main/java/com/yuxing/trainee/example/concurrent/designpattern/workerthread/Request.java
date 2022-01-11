package com.yuxing.trainee.example.concurrent.designpattern.workerthread;

import java.util.Random;

/**
 * 请求，代表一个用户请求(待执行的工作)
 *
 * @author yuxing
 * @since 2022/1/11
 */
public class Request {

    private static final Random random = new Random();

    /**
     * 委托者
     */
    private final String name;

    /**
     * 请求编号
     */
    private final int number;

    public Request(String name, int number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Request{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';
    }

    public void execute() {
        System.err.println(Thread.currentThread().getName() + " execute " + this);
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

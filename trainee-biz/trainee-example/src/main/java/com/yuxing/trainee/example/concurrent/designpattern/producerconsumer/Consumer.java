package com.yuxing.trainee.example.concurrent.designpattern.producerconsumer;

import java.util.Random;

/**
 * 顾客
 *
 * @author yuxing
 * @since 2021/12/27
 */
public class Consumer implements Runnable {

    private final String name;
    private final Table table;
    private final Random random;

    public Consumer(String name, Table table, int seed) {
        this.name = name;
        this.table = table;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(this.random.nextInt(1000));
                String take = table.take();
                System.err.println(name + " buy a cake: " + take);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

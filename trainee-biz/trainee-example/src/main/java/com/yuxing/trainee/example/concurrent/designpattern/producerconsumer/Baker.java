package com.yuxing.trainee.example.concurrent.designpattern.producerconsumer;

import java.util.Random;

/**
 * 面包师
 *
 * @author yuxing
 * @since 2021/12/27
 */
public class Baker implements Runnable {

    private static int id = 0;

    private final String name;
    private final Table table;
    private final Random random;

    public Baker(String name, Table table, int seed) {
        this.name = name;
        this.table = table;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(this.random.nextInt(1000));
                int i = nextId();
                String cake = "[ Cake No." + i + " by " + this.name + "]";
                table.put(cake);
                System.err.println(this.name + " make a cake. No." + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int nextId() {
        return ++id;
    }
}

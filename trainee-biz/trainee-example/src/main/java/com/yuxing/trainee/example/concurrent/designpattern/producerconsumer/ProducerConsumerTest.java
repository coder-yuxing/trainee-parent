package com.yuxing.trainee.example.concurrent.designpattern.producerconsumer;

/**
 * @author yuxing
 * @since 2021/12/27
 */
public class ProducerConsumerTest {

    public static void main(String[] args) {
        Table table = new Table(3);
        new Thread(new Baker("marker-1", table, 1111)).start();
        new Thread(new Baker("marker-2", table, 1231)).start();
        new Thread(new Baker("marker-3", table, 1321)).start();

        new Thread(new Consumer("consumer-1", table, 2341)).start();
        new Thread(new Consumer("consumer-2", table, 1231)).start();
        new Thread(new Consumer("consumer-3", table, 1234)).start();
    }
}

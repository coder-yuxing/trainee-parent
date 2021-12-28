package com.yuxing.trainee.example.concurrent.designpattern.immutable;

/**
 * @author yuxing
 * @since 2021/12/28
 */
public class PrintPersonThread extends Thread {

    private Person person;

    public PrintPersonThread(Person person) {
        this.person = person;
    }

    @Override
    public void run() {
        while (true) {
            System.err.println(Thread.currentThread().getName() + " prints " + person);
        }
    }
}

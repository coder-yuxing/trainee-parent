package com.yuxing.trainee.common.concurrent;

public class JavaBasicTest {

    private volatile Integer num = 0;

    public static void main(String[] args) {

        JavaBasicTest javaBasicTest = new JavaBasicTest();
        new Thread(javaBasicTest::numPlus).start();
        new Thread(javaBasicTest::numPlus).start();

    }

    private void numPlus() {
        num = num + 1;
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println(Thread.currentThread().getName() + ": " + num);
    }
}

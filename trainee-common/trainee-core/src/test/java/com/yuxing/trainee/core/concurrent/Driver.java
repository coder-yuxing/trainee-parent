package com.yuxing.trainee.core.concurrent;

import java.util.concurrent.CountDownLatch;



public class Driver { // ...

    public static void main(String[] args) throws Exception {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(2);

        for (int i = 0; i < 2; ++i) // create and start threads
            new Thread(new Worker(startSignal, doneSignal, i)).start();

        System.err.println("start");
        startSignal.countDown();      // let all threads proceed
        doneSignal.await();           // wait for all to finish
        System.err.println("end");
    }

}

class Worker implements Runnable {
    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;
    private final int index;
    Worker(CountDownLatch startSignal, CountDownLatch doneSignal, int index) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
        this.index = index;
    }
    public void run() {
        try {
            startSignal.await();
            doWork();
            doneSignal.countDown();
        } catch (InterruptedException ex) {} // return;
    }

    void doWork() {
        System.err.println("index=" + index);
    }
}
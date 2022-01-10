package com.yuxing.trainee.example.concurrent.designpattern.twophasetermination;

/**
 * @author yuxing
 * @since 2022/1/10
 */
public class CountupThread extends Thread {

    /**
     * 技术值
     */
    private long counter = 0;

    /**
     * 请求终止状态，在发出终止请求后变为true
     */
    private volatile boolean shutdownRequested = false;

    public void shutdownRequest() {
        this.shutdownRequested = true;
        // interrupt()方法：该方法只是改变中断状态。它不会中断一个正在运行的线程
        // 该方法实际完成的是：给受阻塞的线程发出一个中断信号，这样受阻塞的线程就得以退出阻塞的状态
        // 更确切的说，如果线程被Object.wait、Thread.join、Thread.sleep三种方法之一阻塞，
        // 此时调用该线程的interrupt() 方法，那么该线程将抛出一个 InterruptedException 中断异常。
        // 从而提早地终结被阻塞状态。如果线程没有被阻塞，这是调用interrupt()方法是不起作用的，
        // 直到执行到wait()、sleep()、join()时，才会马上抛出 InterruptedException
        //
        // 为什么要在此处调用interrupt方法(换言之，为什么只将shutdownRequested标志设置为true时不行的)？
        // 原因很简单，因为当想要终止线程时，该线程可能正在sleep。而当线程正在sleep时，即使将shutdownRequested标志设置为true
        // 线程也不会开始终止处理。等到sleep时间过后，线程可能会在某个时间点开始终止处理，但这样程序的响应性就下降了
        // 如果使用 interrupt() 方法，就可以中断sleep
        // 仅仅是调用该方法是不够的，必须捕获InterruptedException异常
        this.interrupt();
    }

    /**
     * 检查是否发出了终止请求
     *
     * @return 请求终止状态
     */
    public boolean isShutdownRequested() {
        return this.shutdownRequested;
    }

    @Override
    public final void run() {
        try {
            while (!this.isShutdownRequested()) {
                this.doWork();
            }
        } catch (Exception e) {
            // do something
            e.printStackTrace();
        } finally {
            this.doShutdown();
        }
    }

    private void doWork() throws Exception {
        this.counter++;
        System.err.println("doWork: counter = " + this.counter);
        Thread.sleep(500);
    }

    private void doShutdown() {
        System.err.println("doShutdown： counter = " + this.counter);
    }

    public static void main(String[] args) {
        System.err.println("main: BEGIN");
        try {
            CountupThread t = new CountupThread();
            t.start();
            Thread.sleep(10000);
            System.err.println("main: shutdownRequest");
            t.shutdownRequest();

            System.err.println("main: join");
            t.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.err.println("main: END");
    }
}

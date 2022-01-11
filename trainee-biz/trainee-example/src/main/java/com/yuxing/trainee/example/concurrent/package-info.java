/**
 * 学习Java 并发编程涉及到的代码示例
 * 学习要：“跳出来看全景，钻进去看本质”<br />
 *
 * 并发编程领域可以抽象成三个核心问题：分工、同步和互斥<br/>
 * <br/>
 * ① 分工 <br/>
 * 所谓分工，类似于现实中一个组织完成一个项目，项目经理要拆分任务，安排合适的成员去完成。<br/>
 * <br/>
 * Java中的 {@link java.util.concurrent.Executor}、Fork/Join: {@link java.util.concurrent.ForkJoinPool}、
 * {@link java.util.concurrent.Future} 本质上都是一种分工，除此之外，并发编程领域还总结了一些设计模式，基本上都是和
 * 分工相关的：生产者-消费者、Thread-Per-Message、Worker Thread <br/>
 * <br/>
 *
 * ② 同步(协作) <br/>
 * 分工完成后，就是具体执行了，在项目执行过程总，任务之间是由依赖的，一个任务结束后，依赖它的后续任务就可以开工了，后续工作
 * 开工如何通知？ <br/>
 * <br/>
 * 在并发编程领域里的同步，主要是指线程如何协作，本质上和现实生活中的协作并没有什么区别，不过是“一个线程执行完了一个任务，如
 * 何通知执行后续任务的线程开工”而已。 <br/>
 * <br/>
 * Java中的{@link java.util.concurrent.CountDownLatch}、{@link java.util.concurrent.CyclicBarrier}、
 * {@link java.util.concurrent.Phaser}、{@link java.util.concurrent.Exchanger} 等都是用于处理线程间协作问题的 <br/>
 * <br/>
 * 工作中遇到的线程协作问题，基本上都可以描述为这样一个问题：<br/>
 * “当某个条件不满足时，线程需要等待；当某个条件满足时，线程需要被唤醒执行” <br/>
 * <br/>
 * 解决协作问题的核心技术是：“管程”，管程是解决并发问题的万能钥匙 <br/>
 * <br/>
 * ③ 互斥 <br/>
 * 分工、同步主要强调的是性能，但并发程序中还有一部分是关于正确性的 -> “线程安全” <br/>
 * <br/>
 * 所谓线程安全，是指同一时刻，只允许一个线程访问共享变量 <br/>
 * <br/>
 * @author yuxing
 * @since 2022/1/11
 */
package com.yuxing.trainee.example.concurrent;
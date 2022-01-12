package com.yuxing.trainee.common.core.concurrent;


import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <b>为什么需要线程池？</b><br/>
 * Java 语言中，java线程和操作系统线程是一一对应的，这种做法本质上是将java线程的调度权委托给操作系统，
 * 而操作系统在这方面非常成熟，所以这种做法的好处就是稳定、可靠，但也同样继承了操作系统线程的缺点：创建成本高。 <br/>
 * 线程池是实现线程复用的一种有效方式。<br/>
 * <br/>
 *
 * <b>自定义线程池{@link CustomThreadPoolExecutor}实现说明：</b><br/>
 * ① 该线程池实现了{@link Executor}接口，其本质是实现了任务发布与任务执行的解耦，任务发布者只需关心业务逻
 * 辑的实现，并通过{@link Executor#execute(Runnable)}完成任务发布，隐藏了任务的执行与线程管理的细节。<br/>
 * <br/>
 *
 * ② 线程池中各参数的作用：<br/>
 * {@link CustomThreadPoolExecutor#maxPoolSize}: 1.线程是有限资源，我们不能无限制的创建线程，因此在创
 * 建线程池时我们需要为其设定一个最大数量，以避免在使用过程中资源耗尽；2.当任务队列空间耗尽，仍存在用户提交的任
 * 务无法处理时(任务积压)，线程池需提供一定的弹性空间允许在大量任务积压时，增加线程数量来处理任务，但仍需限制新
 * 增线程数量,当期达到上限就不再创建。 <br/>
 * {@link CustomThreadPoolExecutor#corePoolSize}: 线程在运行过程中可能只是偶发性地大批量提交任务，而大
 * 部分时间只是比较零散、少量的任务提交，这就可能导致线程池中线程在完成任务后一段时间内处于空闲状态。如果线程池
 * 中线程创建后一直存活会导致资源浪费。因此，当线程空闲超过一定时间({@link CustomThreadPoolExecutor#keepAliveTime})
 * 后应及时将其销毁以回收系统资源；另外，为避免频繁地创建和销毁线程，线程池中也需要缓存一定数量的线程即使处于空闲
 * 状态也不回收，这类线程我们就称之为核心线程，相应的线程数量就称之为核心线程池大小(corePoolSize)，超出核心线程
 * 数量之外被创建的线程被称为非核心线程。<br/>
 * {@link CustomThreadPoolExecutor#taskQueue} 当核心线程已全部创建，仍无法及时处理用户提交的任务，需将
 * 其临时存放至任务队列，以便后续线程空闲时处理 <br/>
 * {@link CustomThreadPoolExecutor#abortPolicy} 任务放弃策略用于在线程资源已耗尽仍存在无法及时处理的任务时，
 * 提供一种兜底策略来处理这部分任务 <br/>
 * {@link CustomThreadPoolExecutor#keepAliveTime} 当任务处理完毕后，一定时间内仍未有新任务时，需及时释
 * 放掉空闲的资源，keepLiveTime用于控制空闲资源的存活时间 <br/>
 * <br/>
 *
 * ③ 线程池工作流程：<br/>
 *  1. 任务提交时，优先创建核心线程来处理任务 <br/>
 *  2. 核心线程资源耗尽，则将任务加入阻塞队列，等待空闲核心线程处理 <br/>
 *  3. 若阻塞队列资源耗尽，仍存在任务无法及时处理，则尝试创建非核心线程进行处理 <br/>
 *  4. 若非核心线程资源耗尽，剩余任务通过任务放弃策略进行处理 <br/>
 *  5. 当任务处理完毕后，一段时间后无法获取任务，则将闲置资源(非核心线程)释放 <br/>
 *  6. 对于 1&3 中创建的线程，当线程创建后即加入线程池中，并开启线程轮询获取任务，并进行执行，在无法获取任务后
 *  等待资源释放 <br/>
 * <br/>
 *
 * ④ 采用生产者-消费者模型{@link com.yuxing.trainee.example.concurrent.designpattern.producerconsumer}
 * 完成任务的提交 & 任务的消费 <br/>
 *  - {@link CustomThreadPoolExecutor#execute(Runnable)} 完成生产者角色 <br/>
 *  - {@link Worker} 完成消费者角色 <br/>
 * <br/>
 *
 * ⑤ 采用Worker Thread模式{@link com.yuxing.trainee.example.concurrent.designpattern.workerthread} <br/>
 * <br/>
 *
 * <b>如何实现线程复用？</b><br/>
 * 线程在执行完 {@link Thread#run()}方法后就会被回收，因此要想实现线程复用，就应避免退出run()方法。这里，我们可以
 * 通过<b>循环向任务队列获取任务</b>的方式来实现；另一方面，若任务队列为空时，无法获取任务仍可能导致线程退出run()方法，
 * 这种情况下，我们可以通过阻塞线程的方式，等待外部提交任务。通过<b>循环获取+阻塞等待</b>的方式，就可以实现线程复用的
 * 目的。<br/>
 * <br/>
 * <b>如何尽量实现线程复用？(为什么在线程池工作流程中在核心线程资源耗尽后不继续创建线程而是优先放入任务队列，待队列满了
 * 才继续创建非核心线程？)</b><br/>
 * 线程池中需要使用到任务队列进行缓存不能及时处理的任务，那么任务队列的使用时机就可以有一下两种：<br/>
 * 1.当线程数已达到maximumPoolSize上限要求，并且所有线程都处于工作状态时，此后外部提交的任务才被缓存到任务队列中<br/>
 * 2.当核心线程corePoolSize达到上限要求时，此后外部提交的任务就被缓存到任务队列中，当任务队列满了之后，才通过创建
 *   非核心线程来处理任务<br/>
 * 显然第二种方案更优，此方案一方面尽可能的实现了资源(核心线程)的最大化复用，另一方面可以避免创建非核心线程(少占用系
 * 统稀缺资源); 并且该方案在应对“偶发性地大批量提交任务，大部分时间只是比较零散地少量任务提交”这种情况较为合适。但该方案
 * 在一定程度上降低了任务的处理速度。<br/>
 * <br/>
 *
 * TODO(yuxing):
 *   ① 缺少线程池生命周期管理
 *   ② 采用Two-Phase Termination 模式{@link com.yuxing.trainee.example.concurrent.designpattern.twophasetermination} 关闭线程
 *   ③ 扩展 {@link com.yuxing.trainee.common.core.concurrent.CustomThreadPoolExecutor} 使其支持执行任务并获取返回值
 *   ④ 任务放弃策略处理
 *
 *
 * @author yuxing
 * @since 2021/12/21
 */
@Slf4j
public class CustomThreadPoolExecutor implements Executor {

    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 线程名称前缀
     */
    private final String threadNamePrefix;

    /**
     * 核心线程数
     */
    private final int corePoolSize;

    /**
     * 最大线程数
     */
    private final int maxPoolSize;

    /**
     * 线程存活时间
     */
    private final long keepAliveTime;

    /**
     * 阻塞队列
     *
     * 阻塞队列用于平衡任务发布于任务处理二者间的效率差异
     */
    private final BlockingQueue<Runnable> taskQueue;

    /**
     * 任务抛弃策略
     */
    private final int abortPolicy;

    /**
     * 工作线程集合
     *
     * 线程池需要管理线程的生命周期，需要在线程长时间不运行时进行资源回收
     * 此处通过workers集合持有线程池中正在运行线程的引用，就可以通过添加引用、移除引用这样的操作来控制线程的生命周期
     */
    private final Set<Worker> workers;

    /**
     * 工作线程数量
     */
    private final AtomicInteger workerCount = new AtomicInteger(0);

    public CustomThreadPoolExecutor(int corePoolSize, int maxPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> taskQueue, int abortPolicy) {
        this("custom-thread-", corePoolSize, maxPoolSize, keepAliveTime, unit, taskQueue, abortPolicy);
    }

    public CustomThreadPoolExecutor(String threadNamePrefix, int corePoolSize, int maxPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> taskQueue, int abortPolicy) {
        this.threadNamePrefix = threadNamePrefix;
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.taskQueue = taskQueue;
        this.abortPolicy = abortPolicy;
        this.keepAliveTime = unit.toMillis(keepAliveTime);
        this.workers = new HashSet<>(this.maxPoolSize);
    }

    @Override
    public void execute(Runnable command) {

        if (command == null) {
            throw new NullPointerException();
        }

        // 1.尝试创建核心线程执行任务
        int runningThreadNum = workerCount.get();
        if (runningThreadNum < this.corePoolSize
                && this.addWorker(command, true)) {
            // 若核心线程创建成功，则直接返回
            return;
        }

        // 2.创建核心线程执行任务失败，则尝试将任务加入阻塞队列
        if (this.taskQueue.offer(command)) {
            return;
        }

        // 3.阻塞队列已满，则尝试创建非核心线程执行任务
        runningThreadNum = workerCount.get();
        if (runningThreadNum < this.maxPoolSize
                && this.addWorker(command, false)) {
            return;
        }

        // 4.若非核心线程已创建完毕，则根据任务抛弃策略放弃任务
        this.abortTask(command);
    }


    private boolean addWorker(Runnable firstTask, boolean core) {
        int maxNum = core ? this.corePoolSize : this.maxPoolSize;
        int runningThreadNum = this.workerCount.get();
        if (runningThreadNum < maxNum) {
            Worker worker = new Worker(firstTask);
            final Thread thread = worker.thread;
            if (thread != null) {
                lock.lock();
                try {
                    this.workers.add(worker);
                    int workerNum = this.workerCount.incrementAndGet();
                    thread.setName(this.threadNamePrefix + workerNum);
                    thread.start();
                    return true;
                } finally {
                    lock.unlock();
                }
            }
        }
        return false;
    }

    private void abortTask(Runnable command) {
        // do something
        log.debug("abortTask executed");
    }

    class Worker implements Runnable {

        public Worker(Runnable firstTask) {
            this.firstTask = firstTask;
            this.thread = new Thread(this);
        }

        private final Runnable firstTask;

        private final Thread thread;

        @Override
        public void run() {
            try {
                // 1.若存在firstTask,执行firstTask
                // 2.不存在firstTask 或已执行完毕，则尝试从工作队列中获取任务继续执行
                Runnable task = firstTask;
                while (task != null || (task = getTask()) != null) {
                    try {
                        log.debug("{} running", this.thread.getName());
                        task.run();
                    } finally {
                        task = null;
                    }
                }
            } finally {
                lock.lock();
                try {
                    // 未获取到任务，则从工作线程集合中删除当前线程
                    log.debug("指定时间内未获取到任务，线程{}执行完毕，即将释放资源", this.thread.getName());
                    workers.remove(this);
                    workerCount.decrementAndGet();
                } finally {
                    lock.unlock();
                }
            }
        }

        private Runnable getTask() {
            try {
                // poll 方法取走BlockingQueue中排首位的对象，若不能立即获取，则可等待 keepLiveTime 规定的时间，超时时返回null
                // 通过该操作控制了线程池内线程的存活时间
                // 当前实现中未区分核心线程与非核心线程的存活时间控制
                return taskQueue.poll(keepAliveTime, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
               log.error("获取任务异常，返回空", e);
               return null;
            }
        }
    }
}

package com.yuxing.trainee.example.concurrent.designpattern.guardedsuspension;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * Guarded Suspension 模式是指在多线程环境中，当线程执行条件不满足时暂时挂起，等待条件满足时再唤醒继续执行的操作
 *
 * 该模式本质上是一种等待唤醒机制，是一种多线程模式下的if判断
 *
 * @author yuxing
 * @since 2021/12/23
 */
@Slf4j
public class GuardedObject<T> {

    private final ReentrantLock lock = new ReentrantLock();

    private final Condition condition = lock.newCondition();

    /**
     * 受保护对象
     */
    private T obj;

    /**
     * 等待超时时间
     */
    private final long timeout;

    /**
     * 时间单位
     */
    private final TimeUnit timeUnit;

    public GuardedObject() {
        this(1, TimeUnit.SECONDS);
    }

    public GuardedObject(long timeout, TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        this.timeout = timeout;
    }

    /**
     * 获取受保护对象
     *
     * @param predicate 限制条件
     * @return 受保护对象
     */
    public T get(Predicate<T> predicate) {
        lock.lock();
        try {
            while (!predicate.test(this.obj)) {
                condition.await(this.timeout, this.timeUnit);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return this.obj;
    }

    /**
     * 事件通知方法
     *
     * @param obj 受保护对象
     */
    public void onChanged(T obj) {
        lock.lock();
        try {
            this.obj = obj;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }


    private static final Map<Object, GuardedObject<?>> guardedObjectMap = new ConcurrentHashMap<>();

    public static <K, V> GuardedObject<V> create(K key)  {
        GuardedObject<V> guardedObject = new GuardedObject<>();
        guardedObjectMap.put(key, guardedObject);
        return guardedObject;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> void fireEvent(K key, V obj) {
        GuardedObject<V> guardedObject = (GuardedObject<V>) guardedObjectMap.get(key);
        if (guardedObject != null) {
            guardedObject.onChanged(obj);
        }
    }

}

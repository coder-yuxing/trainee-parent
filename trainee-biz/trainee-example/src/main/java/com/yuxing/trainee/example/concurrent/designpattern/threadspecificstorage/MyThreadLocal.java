package com.yuxing.trainee.example.concurrent.designpattern.threadspecificstorage;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ThreadLocal 的目的是通过为每个线程创建一个本地副本来避免共享，从而避免多线程竞争问题，那么一种可能的实现方案是：
 * 每个ThreadLocal中维护一个Map，Map中的键就是Thread,值是它在该Thread中的实例。
 *
 * 该方案可满足每个线程存在一个独立备份的要求，在每个线程访问该ThreadLocal时，需要项Map中添加一个映射，在执行结束时
 * 需要及时清除该映射，这里就存在两个问题：
 * - 在增加/减少线程时，均需要写Map，因此就要保证Map线程安全
 * - 线程结束时需要保证它所访问的所有ThreadLocal 中对应的映射均删除，否则可能导致内存泄漏
 *
 *  在我们的设计方案中，ThreadLocal持有的Map会持有Thread的引用，这就意味着只要ThreadLocal对象存在，那么Map中的
 *  Thread对象就不会被回收。然而在实际使用中，ThreadLocal的声明周期往往都比线程药厂，因此这种方案容易导致内存泄漏
 * @author yuxing
 * @since 2021/12/31
 */
public class MyThreadLocal<T> {

    private final ConcurrentHashMap<Thread, T> locals = new ConcurrentHashMap<>();

    T get() {
        return locals.get(Thread.currentThread());
    }

    void set(T obj) {
        locals.put(Thread.currentThread(), obj);
    }
}

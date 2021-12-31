/**
 * 线程本地存储 本质上这是一种避免共享的方案，由于不存在共享资源，所以自然不会存在并发竞争问题
 * 如果你需要在并发场景中安全的使用一种线程不安全的工具类，最简单的方案就是避免共享。而避免共享有两种方案：
 *   - 一种方案是将该工具类作为局部变量使用，该方案缺点在于在高并发场景下会频繁创建对象
 *   - 另一种方案就是线程本地存储模式，该方案避免了第一种方案存在的问题
 *
 * TODO(yuxing): ThreadLocal原理，内存泄漏问题
 * ThreadLocal 是Java提供的基于线程本地存储模式的一种实现，
 * @author yuxing
 * @since 2021/12/30
 */
package com.yuxing.trainee.example.concurrent.designpattern.threadspecificstorage;
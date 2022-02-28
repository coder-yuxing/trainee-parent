package com.yuxing.trainee.example.designpattern.singleton;

/**
 * 静态内部类实现单例
 *
 * @author yuxing
 * @since 2022/2/28
 */
public class StaticInnerClassSingleton {

    public static class Holder {
        private static final StaticInnerClassSingleton INSTANCE = new StaticInnerClassSingleton();
    }

    private StaticInnerClassSingleton() {

    }

    public static StaticInnerClassSingleton getInstance() {
        return Holder.INSTANCE;
    }
}

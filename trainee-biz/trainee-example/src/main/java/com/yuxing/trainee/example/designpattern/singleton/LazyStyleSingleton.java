package com.yuxing.trainee.example.designpattern.singleton;

/**
 * 懒汉式单例实现示例
 *
 * @author yuxing
 * @since 2022/2/28
 */
public class LazyStyleSingleton {

    /**
     * volatile 保证可见性、有序性
     */
    private volatile static LazyStyleSingleton instance = null;

    private LazyStyleSingleton() {}

    public static LazyStyleSingleton getInstance() {
        // 双重检查避免不必要的同步操作
        // 在第一次使用的时候才进行初始化，达到了懒加载的效果；在进行初始化的时候会进行同步
        if (instance == null) {
            synchronized (LazyStyleSingleton.class) {
                if (instance == null) {
                    instance = new LazyStyleSingleton();
                }
            }
        }
        return instance;
    }

}

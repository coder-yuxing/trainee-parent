package com.yuxing.trainee.example.designpattern.singleton;

/**
 * 饿汉式单例实现示例
 *
 * @author yuxing
 * @since 2022/2/28
 */
public class HungryManSingleton {

    private static final HungryManSingleton INSTANCE = new HungryManSingleton();

    /**
     * 私有化构造器，避免其他地方实例化该类
     */
    private HungryManSingleton() {}

    public static HungryManSingleton getInstance() {
        return INSTANCE;
    }


}

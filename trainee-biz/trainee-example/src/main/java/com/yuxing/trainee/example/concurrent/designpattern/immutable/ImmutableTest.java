package com.yuxing.trainee.example.concurrent.designpattern.immutable;

/**
 * @author yuxing
 * @since 2021/12/28
 */
public class ImmutableTest {

    public static void main(String[] args) {

        // 对于适用于Immutable模式的类，我们无需再使用如 synchronized方法执行线程互斥，
        // 因为即使不适用 synchronized也能确保线程安全
        // final 的含义：
        // 1. final类：若类声明中加上final,则表示该类无法扩展，即无法创建final类的子类
        //    因此，final类中声明的方法也不会被重写
        // 2. final方法：若实例方法的声明中加上final，则表示该方法不会被子类的方法重写
        //    若在静态方法声明中加上final，则表示该方法不会被子类的方法隐藏。
        //    在模板方法模式中，有时模板方法会被声明为final方法
        // 3. final字段：final字段只能被复制一次
        Person person = new Person("alice", "alaska");
        new PrintPersonThread(person).start();
        new PrintPersonThread(person).start();
        new PrintPersonThread(person).start();
    }
}

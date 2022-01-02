package com.yuxing.trainee.example.concurrent.lock;

/**
 * final 关键字重排序规则
 *
 * @author yuxing
 * @since 2022/1/2
 */
public class FinalExample {

    int i;
    final int j;
    static FinalExample obj;

    public FinalExample() {
        this.i = 1; // 写普通域
        this.j = 2; // 写final域
    }

    /**
     * 该方法 FinalExample obj = new FinalExample(); 包含两个步骤：
     * 1. 构造一个FinalExample类型的对象
     * 2. 将该对象的引用赋值给变量obj
     */
    public static void writ() {     // 写线程A执行
        obj = new FinalExample();
        // do something
    }

    public static void read() {     // 读线程B执行
        FinalExample object = obj;  // 读对象引用
        int a = object.i;           // 读普通域
        int b = object.j;           // 读final域
    }
}

package com.yuxing.trainee.example.concurrent.designpattern.balking;

/**
 * Balking 模式简单实例程序：
 * 该程序用于模拟定期将数据内容写入文件的操作，当数据内容被写入时，会覆盖上次写入的内容，只有最新内容才会被保存。
 * 另外，当写入内容与上次写入内容完全相同时，再向文件写入内容就显得多余了，因此就不再执行写入操作。也就是说，该
 * 程序以“数据内容存在不同”作为守护条件，数据相同就不执行写入，直接返回(balk)
 *
 * {@link Data} 守护对象，表示可以修改并保存的数据类
 * {@link ChangerThread} 模拟修改并保存数据的对象
 * {@link SaverThread} 模拟守护线程，定期保存数据
 *
 * @author yuxing
 * @since 2021/12/24
 */
public class BalkingTest {


    public static void main(String[] args) {
        // 从运行结果看，有时是SaverThread调用doSave方法，有时是ChangerThread调用doSave方法
        // 但无论哪种情况，content内容都不会重复保存，这是因为当content内容相同时，线程就会balk，不会再调用doSave方法
        Data data = new Data("data.txt", "(empty)");
        new Thread(new ChangerThread(data), "ChangerThread").start();
        new Thread(new SaverThread(data), "SaverThread").start();
    }

}

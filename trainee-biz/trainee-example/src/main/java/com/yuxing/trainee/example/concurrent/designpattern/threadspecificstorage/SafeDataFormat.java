package com.yuxing.trainee.example.concurrent.designpattern.threadspecificstorage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 线程安全的 DataFormat
 *
 * 我们知道，SimpleDateFormat是线程不安全的，想要在并发场景中安全的使用它，可以考虑使用本地线程存储的方式避免共享
 *
 * @author yuxing
 * @since 2021/12/30
 */
public class SafeDataFormat {

    static final ThreadLocal<DateFormat> tl = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyy-MM-dd HH:mm:ss"));

    public static DateFormat get() {
        return tl.get();
    }
}

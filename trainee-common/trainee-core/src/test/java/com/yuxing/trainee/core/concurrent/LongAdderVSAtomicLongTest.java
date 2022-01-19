package com.yuxing.trainee.core.concurrent;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * LongAdder 与 AtomicLong 性能测试
 *
 * JMH注解介绍：
 *  https://www.zhihu.com/question/276455629/answer/1259967560
 *
 * @author yuxing
 * @since 2021/12/15
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS) // 预热1轮，每次1s
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS) // 测试5轮， 每次5s
@Fork(1)
@State(Scope.Benchmark)
@Threads(1000)
public class LongAdderVSAtomicLongTest {

    @Test
    public void test() throws Exception {
        // 启动基准测试
        Options opt = new OptionsBuilder()
                .include(LongAdderVSAtomicLongTest.class.getSimpleName()) // 要导入的测试类
                .build();
        new Runner(opt).run(); // 执行测试
    }

    @Benchmark
    public void atomicLongTest(Blackhole blackhole) {
        AtomicInteger atomicInteger = new AtomicInteger();
        for (int i = 0; i < 1024; i++) {
            atomicInteger.addAndGet(1);
        }
        // 为了避免 JIT 忽略未被使用的结果
        blackhole.consume(atomicInteger.intValue());
    }

    @Benchmark
    public void longAdderTest(Blackhole blackhole) {
        LongAdder longAdder = new LongAdder();
        for (int i = 0; i < 1024; i++) {
            longAdder.add(1);
        }
        blackhole.consume(longAdder.intValue());
    }

}

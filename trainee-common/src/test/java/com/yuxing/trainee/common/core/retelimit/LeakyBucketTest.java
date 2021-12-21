package com.yuxing.trainee.common.core.retelimit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class LeakyBucketTest {

    @Test
    public void basicTest() {
        LeakyBucket bucket = LeakyBucket.monitor(System.out::println);

        bucket.invoke("Hello world");
    }

    @Test
    public void failsAreIncreasedOnError() {
        LeakyBucket bucket = LeakyBucket.monitor(__ -> { throw new RuntimeException(""); });

        try {
            bucket.invoke("");
        } catch (OperationFailedException ignore) {}

        assertEquals(1, bucket.getCurrentFailCount());
    }

    @Test
    public void failsAreDecreasedPeriodically() throws InterruptedException {
        LeakyBucket bucket = LeakyBucket.monitor(__ -> { throw new RuntimeException(""); });

        try {
            bucket.invoke("");
        } catch (OperationFailedException ignore) {}

        Thread.sleep(LeakyBucket.COUNTDOWN_RATE + 1);

        assertEquals(0, bucket.getCurrentFailCount());
    }

    @Test
    public void tooHighErrorRateCausesBucketToOverflow() {
        LeakyBucket bucket = LeakyBucket.monitor(__ -> { throw new RuntimeException(""); }, 10);
        boolean thrown = false;

        for (int i = 0; i <= 10; ++i) {
            try {
                    bucket.invoke("");
            }
            catch (OperationFailedException ignore) {}
            catch (LeakyBucket.BucketOverflowException e) {
                thrown = true;
            }
        }

        assertTrue(thrown);
    }

}
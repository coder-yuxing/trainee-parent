package com.yuxing.trainee.common.lock.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RedisDistributedLockTest {

    static int n = 500;

    @Test
    public void acquire() {

        Runnable runnable = () -> {
            com.yuxing.trainee.common.lock.redis.RedisDistributedLock lock = null;
            String lockId = null;
            try (Jedis conn = new Jedis("127.0.0.1", 6379)) {
                JedisClient jedisClient = new JedisClient(conn);
                lock = new com.yuxing.trainee.common.lock.redis.RedisDistributedLock(jedisClient, "test1", 10);
                lockId = lock.acquire();
                if (lockId != null) {
                    System.out.println(Thread.currentThread().getName() + "正在运行");
                    this.secskill();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (lock != null && lockId != null) {
                    lock.release(lockId);
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(runnable);
            t.start();
        }
    }

    private void secskill() throws Exception {
        if (n == 495) {
            Thread.sleep(8 * 1000);
        }
        System.out.println("n = " + --n);
    }

    @Slf4j
    static class JedisClient implements com.yuxing.trainee.common.lock.redis.RedisClient {

        private final Jedis jedis;

        public JedisClient(Jedis jedis) {
            this.jedis = jedis;
        }

        @Override
        public String get(String key) {
            return jedis.get(key);
        }

        @Override
        public boolean eval(String script, List<String> keys, String... args) {
            Object status = jedis.eval(script, keys, Arrays.asList(args));
            log.info("eval success, script: {}, keys: {}, args: {}, status: {}", script, keys, args, status);
            return SUCCESS_STATUS.equals(status);
        }

        public static void main(String[] args) {
            final String script = "if redis.call('set', KEYS[1], ARGV[1], 'EX', ARGV[2], 'NX') then return 1 else return 0 end";
            Jedis conn = new Jedis("127.0.0.1", 6379);
            JedisClient jedisClient = new JedisClient(conn);
            boolean test = jedisClient.eval(script, Collections.singletonList("test"), "11111", "30");
            System.err.println(test);
        }
    }
}
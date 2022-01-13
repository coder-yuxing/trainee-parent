package com.yuxing.trainee.common.core.lock.zookeeper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZkDistributedLockTest {
    static int n = 500;

    public static void secskill() throws Exception {
//        if (n == 495) {
//            Thread.sleep(8 * 1000);
//        }
        System.out.println("n = " + --n);
    }

    public static void main(String[] args) {

        // Runnable runnable = () -> {
        //     ZookeeperDistributedLock lock = null;
        //     String lockId = null;
        //     ZkClient zkClient = null;
        //     try {
        //         zkClient = new ZkClient("10.10.32.46:2181", 10000);
        //         lock = new ZookeeperDistributedLock(zkClient);
        //         lockId = lock.acquire();
        //         if (lockId != null) {
        //             System.out.println(Thread.currentThread().getName() + "正在运行");
        //             secskill();
        //         }
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     } finally {
        //         if (lock != null && lockId != null) {
        //             lock.release(lockId);
        //         }
        //         if (zkClient != null) {
        //             zkClient.close();
        //         }
        //     }
        // };
        //
        // for (int i = 0; i < 10; i++) {
        //     Thread t = new Thread(runnable);
        //     t.start();
        // }

    }
}
package com.yuxing.trainee.common.core.lock.zookeeper;

import org.I0Itec.zkclient.ZkClient;

public class ZookeeperDistributedLockTest {

    private static final String LOCK_ROOT_PATH = "/lock";
    private static final String LOCK_PATH_PREFIX = LOCK_ROOT_PATH + "/";


    public static void main(String[] args) throws Exception {

        // curator 是Netflix公司开源的一套Zookeeper客户端框架
//        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
//                .connectString("10.10.32.46:2181")
//                .sessionTimeoutMs(4000)
//                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
//                .namespace("")
//                .build();
//
//        curatorFramework.start();
//        Stat stat = new Stat();
//        byte[] bytes = curatorFramework.getData().storingStatIn(stat).forPath("/runoob");
//
//        curatorFramework.close();

        ZkClient zkClient = new ZkClient("10.10.32.46:2181");
        zkClient.createPersistent("/test");
    }
}

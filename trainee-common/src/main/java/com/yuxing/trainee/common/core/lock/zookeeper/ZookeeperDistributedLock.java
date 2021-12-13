package com.yuxing.trainee.common.core.lock.zookeeper;

import cn.hutool.core.util.StrUtil;
import com.yuxing.trainee.common.core.lock.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.*;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 基于 Zookeeper 实现分布式锁
 *
 * @author yuxing
 */
@Slf4j
public class ZookeeperDistributedLock implements DistributedLock {

    private static final String LOCK_ROOT_PATH = "/lock";
    private static final String LOCK_PATH_PREFIX = LOCK_ROOT_PATH + "/";

    /**
     * zk 客户端
     */
    private final ZkClient zkClient;

    public ZookeeperDistributedLock(ZkClient zkClient) {
        this.zkClient = zkClient;
        if (!zkClient.exists(LOCK_ROOT_PATH)) {
            zkClient.createPersistent(LOCK_ROOT_PATH);
        }
    }

    @Override
    public String acquire() {
        String path = null;
        try {
            path = zkClient.createEphemeralSequential(LOCK_PATH_PREFIX, StrUtil.EMPTY);
            List<String> waiters = zkClient.getChildren(LOCK_ROOT_PATH);
            String nowNode = path.substring(LOCK_PATH_PREFIX.length());
            Collections.sort(waiters);
            int index = Collections.binarySearch(waiters, nowNode);
            if (index < 0) {
                throw new IllegalAccessException();
            }
            // 第一个节点默认直接获取锁
            if (index == 0) {
                return path;
            }
            // 未获取锁的节点对其上一个节点增加监听器
            final CountDownLatch latch = new CountDownLatch(1);
            String preWaiter = waiters.get(index - 1);
            zkClient.subscribeChildChanges(LOCK_PATH_PREFIX + preWaiter, new Listener(latch));
            latch.wait();
        } catch (Exception e) {
            log.error("acquire lock error: {}", e.getMessage());
            return null;
        }
        return path;
    }

    @Override
    public boolean release(String lockId) {
        zkClient.delete(lockId, Version.REVISION);
        log.info("lock: {} release success.", lockId);
        return true;
    }

    @Slf4j
    static class Listener implements IZkChildListener {

        private final CountDownLatch latch;

        public Listener(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void handleChildChange(String s, List<String> list) throws Exception {
            log.info("handleChildChange {}", s);
            latch.countDown();
        }
    }
}

package com.yuxing.trainee.common.core.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

/**
 * @author yuxing
 * @since 2022/1/13
 */
public class CuratorZkClientTest {

    private CuratorFramework curatorFramework;
    private CuratorZkClient curatorZkClient;

    @Before
    public void setUp() throws Exception {
        this.createClient();
    }

    /**
     * 创建客户端
     */
    private void createClient() {
        // CuratorFrameworkFactory.builder()
        //         .connectString(connectionString)
        //         .retryPolicy(retryPolicy)
        //         .connectionTimeoutMs(connectionTimeoutMs)
        //         .sessionTimeoutMs(sessionTimeoutMs)
        //         etc. etc.
        //         .build();
        curatorFramework = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
        curatorFramework = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
        curatorFramework.start();

        curatorZkClient = new CuratorZkClient(this.curatorFramework);
    }

    @After
    public void tearDown() throws Exception {
        curatorFramework.close();
    }

    @Test
    public void createPersistentNode() throws Exception {
        String s = this.curatorZkClient.createPersistentNode("/test", "124".getBytes(StandardCharsets.UTF_8));
        System.err.println(s);
    }

    @Test
    public void createPersistentSequentialNode() throws Exception {
        String node = this.curatorZkClient.createPersistentSequentialNode("/test1", "123".getBytes(StandardCharsets.UTF_8));
        System.err.println(node);
    }

    @Test
    public void create() {
    }
}
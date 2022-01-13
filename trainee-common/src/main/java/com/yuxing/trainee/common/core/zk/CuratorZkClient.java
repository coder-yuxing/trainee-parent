package com.yuxing.trainee.common.core.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

/**
 * 基于 CuratorFramework框架操作 zookeeper的工具类
 *
 * @author yuxing
 * @since 2022/1/13
 */
public class CuratorZkClient {

    private final CuratorFramework curatorClient;

    public CuratorZkClient(CuratorFramework curatorClient) {
        this.curatorClient = curatorClient;
    }

    /**
     * 创建持久化节点
     * - 当客户端断开连接时节点不会被自动删除 <br/>
     *
     * @param path    节点路径
     * @param payload 节点内容
     * @return 节点路径名
     * @throws Exception e
     */
    public String createPersistentNode(String path, byte[] payload) throws Exception {
        return curatorClient.create().forPath(path, payload);
    }

    /**
     * 创建持久化顺序节点
     * - 当客户端断开连接时节点不会被自动删除 <br/>
     * - 节点被创建后，其名称会被附加一个单调递增的数字
     *
     * @param path    节点路径
     * @param payload 节点内容
     * @return 节点路径名
     * @throws Exception e
     */
    public String createPersistentSequentialNode(String path, byte[] payload) throws Exception {
        return this.create(path, payload, CreateMode.PERSISTENT_SEQUENTIAL);
    }

    /**
     * 创建特定类型的节点
     *
     * @param path       节点类型
     * @param payload    节点内容
     * @param createMode 节点类型
     * @return 节点路径名
     * @see CreateMode
     * @throws Exception e
     */
    public String create(String path, byte[] payload, CreateMode createMode) throws Exception {
        return curatorClient.create().withMode(createMode).forPath(path, payload);
    }
}

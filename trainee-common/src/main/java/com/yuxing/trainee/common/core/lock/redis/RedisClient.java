package com.yuxing.trainee.common.core.lock.redis;

import java.util.List;

/**
 * redis 客户端接口
 *
 * @author yuxing
 */
public interface RedisClient {

    /**
     * 执行成功时响应状态
     */
    Long SUCCESS_STATUS = 1L;

    /**
     * 根据 key 获取 value; 执行 get key 命令
     * @param key 缓存key
     * @return 缓存值
     */
    String get(String key);

    /**
     * redis eval 命令，执行 eval script numKeys key [key ...] arg [arg ...] 命令；原子操作
     * @param script lua 脚本
     * @param keys   keys集合
     * @param args   参数集合
     * @return 是否执行成功
     */
    boolean eval(final String script, final List<String> keys, final String... args);
}

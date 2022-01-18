package com.yuxing.trainee.common.core.canal;

import java.util.List;

/**
 * canal 服务消息监听抽象接口(消费者)
 *
 * @author yuxing
 * @since 2022/1/18
 */
public abstract class CanalRocketMqListener<T> {

    public final void parseMessage(CanalMessage<T> message) {
        ChangeType changeType = ChangeType.valueOf(message.getType());
        switch (changeType) {
            case INSERT:
                this.doInsert(message.getTable(), message.getData(), message.getIsDdl());
                break;
            case UPDATE:
                this.doUpdate(message.getTable(), message.getData(), message.getIsDdl());
                break;
            case DELETE:
                this.doDeleted(message.getTable(), message.getData(), message.getIsDdl());
                break;
        }
    }

    /**
     * 执行插入操作
     *
     * @param tableName   表名
     * @param changedData 发生变更的数据集合
     * @param isDdl       是否为 ddl(Data definition language, e.g. CREATE DATABASE, CREATE TABLE, ALTER TABLE...)操作
     */
    protected abstract void doInsert(String tableName, List<T> changedData, boolean isDdl);

    /**
     * 执行更新操作
     *
     * @param tableName   表名
     * @param changedData 发生变更的数据集合
     * @param isDdl       是否为 ddl(Data definition language, e.g. CREATE DATABASE, CREATE TABLE, ALTER TABLE...)操作
     */
    protected abstract void doUpdate(String tableName, List<T> changedData, boolean isDdl);

    /**
     * 执行删除操作
     *
     * @param tableName   表名
     * @param changedData 发生变更的数据集合
     * @param isDdl       是否为 ddl(Data definition language, e.g. CREATE DATABASE, CREATE TABLE, ALTER TABLE...)操作
     */
    protected abstract void doDeleted(String tableName, List<T> changedData, boolean isDdl);

    /**
     * 数据变更类型
     */
    protected enum ChangeType {

        /**
         * 插入
         */
        INSERT,

        /**
         * 更新
         */
        UPDATE,

        /**
         * 删除
         */
        DELETE


    }

}

package com.yuxing.trainee.test.application.mq;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuxing.trainee.common.core.canal.CanalMessageHandler;
import com.yuxing.trainee.search.api.goods.command.BatchSaveGoodsDocCommand;
import com.yuxing.trainee.test.application.assembler.GoodsAssembler;
import com.yuxing.trainee.test.domain.repository.GoodsRepository;
import com.yuxing.trainee.test.domain.valueobject.GoodsProp;
import com.yuxing.trainee.test.infrastructure.config.CanalProperties;
import com.yuxing.trainee.test.infrastructure.feign.SearchFeignService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 同步素材数据变更服务类
 *
 * @author yuxing
 * @since 2022/1/18
 */
@Service
@AllArgsConstructor
public class SyncGoodsDataService extends CanalMessageHandler<SyncGoodsDataService.Goods> {

    private final GoodsRepository goodsRepository;
    private final SearchFeignService searchFeignService;

    private final CanalProperties canalProperties;

    @Override
    protected void doInsert(String tableName, List<Goods> changedData, boolean isDdl) {
        Set<String> monitorTables = canalProperties.getMonitorTables();
        if (CollUtil.isEmpty(changedData) || !monitorTables.contains(tableName) || isDdl) {
            return;
        }

        // 删除已标记删除的记录对应的es文档
        Set<Long> deletedIds = changedData.stream().filter(c -> c.deleted == 1).map(c -> c.id).collect(Collectors.toSet());
        deletedIds.forEach(searchFeignService::deleteById);

        // 更新剩余记录的es文档
        List<BatchSaveGoodsDocCommand.Item> collect = changedData.stream().filter(d -> !deletedIds.contains(d.id)).map(d -> {
            com.yuxing.trainee.test.domain.entity.Goods goods = goodsRepository.getById(d.id);
            List<GoodsProp> props = goodsRepository.getPropsById(d.id);
            return GoodsAssembler.INSTANCE.toSaveCommandItem(goods, props);
        }).collect(Collectors.toList());

        searchFeignService.batchSave(new BatchSaveGoodsDocCommand(collect));
    }

    @Override
    protected void doUpdate(String tableName, List<Goods> changedData, boolean isDdl) {
        this.doInsert(tableName, changedData, isDdl);
    }

    @Override
    protected void doDeleted(String tableName, List<Goods> changedData, boolean isDdl) {
        Set<String> monitorTables = canalProperties.getMonitorTables();
        if (CollUtil.isEmpty(changedData) || !monitorTables.contains(tableName) || isDdl) {
            return;
        }
        // 已删除记录不保留其es文档
        changedData.forEach(c -> searchFeignService.deleteById(c.id));
    }

    @Data
    public static class Goods implements Serializable {

        private static final long serialVersionUID = 8536624505942522941L;

        /**
         * ID
         */
        private Long id;

        /**
         * 素材名称
         */
        private String name;

        /**
         * 编码
         */
        private String code;

        /**
         * 1 主材 2 辅材 3 软装
         */
        private Integer type;

        /**
         * 品类ID
         */
        @JsonProperty("category_id")
        private Long categoryId;

        /**
         * 启停用：1 启用 0 停用
         */
        @JsonProperty("is_enabled")
        private Integer enabled;

        /**
         * 是否已删除：1 已删除 0 未删除
         */
        @JsonProperty("is_deleted")
        private Integer deleted;

        /**
         * 创建人ID
         */
        @JsonProperty("create_user")
        private Long createUser;

        /**
         * 创建时间
         */
        @JsonProperty("create_time")
        private String createTime;

        /**
         * 最后编辑人ID
         */
        @JsonProperty("update_user")
        private Long updateUser;

        /**
         * 最后编辑时间
         */
        @JsonProperty("update_time")
        private String updateTime;

    }
}

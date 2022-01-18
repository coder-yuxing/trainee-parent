package com.yuxing.trainee.test.application.mq;

import com.yuxing.trainee.common.core.canal.CanalMessage;
import lombok.AllArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


/**
 * 素材数据变更消息监听
 * rocketmq-spring-boot-starter 消息监听
 *
 * @author yuxing
 * @since 2022/1/17
 */
// @Component
@AllArgsConstructor
// @RocketMQMessageListener(topic = "${trainee-test.canal.consumer.topic}", consumerGroup = "${trainee-test.canal.consumer.group}")
public class CanalGoodsMessageListener implements RocketMQListener<CanalMessage<SyncGoodsDataService.Goods>> {

    private final SyncGoodsDataService syncGoodsDataService;

    @Override
    public void onMessage(CanalMessage<SyncGoodsDataService.Goods> message) {
        syncGoodsDataService.parseMessage(message);
    }
}

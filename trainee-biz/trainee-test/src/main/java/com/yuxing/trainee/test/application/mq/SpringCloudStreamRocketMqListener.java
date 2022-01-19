package com.yuxing.trainee.test.application.mq;

import com.yuxing.trainee.common.core.canal.CanalMessage;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * spring cloud stream rocketmq 消息监听(消费)
 *
 * @author yuxing
 * @since 2022/1/18
 */
// @Component
@AllArgsConstructor
public class SpringCloudStreamRocketMqListener {

    private final SyncGoodsDataService syncGoodsDataService;

    /**
     * 此方法名与配置文件中 function definition 及 {input}-in-{num}保持一致
     */
    // @Bean
    public Function<Flux<Message<CanalMessage<SyncGoodsDataService.Goods>>>, Mono<Void>> canalGoods() {
        return flux -> flux.map(message -> {
            syncGoodsDataService.handle(message.getPayload());
            return message;
        }).then();
    }

}

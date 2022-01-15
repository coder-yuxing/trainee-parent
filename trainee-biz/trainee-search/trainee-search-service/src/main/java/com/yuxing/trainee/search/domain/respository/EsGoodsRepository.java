package com.yuxing.trainee.search.domain.respository;

import com.yuxing.trainee.search.domain.entity.EsGoods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * 素材文档仓储接口
 *
 * @author yuxing
 * @since 2022/1/14
 */
public interface EsGoodsRepository extends ElasticsearchRepository<EsGoods, String> {

}

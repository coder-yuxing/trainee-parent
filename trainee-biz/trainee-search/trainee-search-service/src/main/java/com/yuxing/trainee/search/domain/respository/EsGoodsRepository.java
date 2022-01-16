package com.yuxing.trainee.search.domain.respository;

import com.yuxing.trainee.search.domain.entity.EsGoods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


/**
 * 素材文档仓储接口
 *
 * @author yuxing
 * @since 2022/1/14
 */
public interface EsGoodsRepository extends ElasticsearchRepository<EsGoods, String> {

    /**
     * 根据编码和类型查询素材文档
     * @param code 素材编码
     * @param type 素材类型
     * @return 符合条件的素材文档集合
     */
    List<EsGoods> findByCodeAndType(String code, Integer type);
}

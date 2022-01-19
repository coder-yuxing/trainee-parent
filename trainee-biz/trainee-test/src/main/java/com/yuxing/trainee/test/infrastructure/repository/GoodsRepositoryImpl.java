package com.yuxing.trainee.test.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.yuxing.trainee.test.domain.entity.Goods;
import com.yuxing.trainee.test.domain.repository.GoodsRepository;
import com.yuxing.trainee.test.domain.valueobject.GoodsProp;
import com.yuxing.trainee.test.infrastructure.dao.mapper.GoodsMapper;
import com.yuxing.trainee.test.infrastructure.dao.mapper.GoodsPropMapper;
import com.yuxing.trainee.test.infrastructure.dao.model.GoodsPO;
import com.yuxing.trainee.test.infrastructure.dao.model.GoodsPropPO;
import com.yuxing.trainee.test.infrastructure.repository.assembler.GoodsAssembler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * 素材仓储实现类
 *
 * @author yuxing
 * @since 2022/1/17
 */
@Repository
@AllArgsConstructor
public class GoodsRepositoryImpl implements GoodsRepository {

    private final GoodsMapper goodsMapper;
    private final GoodsPropMapper goodsPropMapper;

    @Override
    public Goods getById(Long id) {
        return GoodsAssembler.INSTANCE.toDo(goodsMapper.getById(id));
    }

    @Override
    public void save(Goods goods) {
        Long id = goods.getId();
        GoodsPO goodsPO = GoodsAssembler.INSTANCE.toPo(goods);
        if (Objects.isNull(id)) {
            goodsMapper.insert(goodsPO);
        } else {
            goodsMapper.updateById(goodsPO);
        }
    }

    @Override
    public void save(Goods goods, List<GoodsProp> props) {
        boolean hasCreated = Objects.nonNull(goods.getId());
        this.save(goods);
        if (CollUtil.isNotEmpty(props)) {
            if (hasCreated) {
                goodsPropMapper.deleteByGoodsId(goods.getId());
            }
            List<GoodsPropPO> propList = GoodsAssembler.INSTANCE.toPropPoList(props, goods.getId());
            goodsPropMapper.batchInsert(propList);
        }
    }

    @Override
    public void removeById(Long id, Long operator) {
        goodsMapper.removeById(id, operator);
    }

    @Override
    public List<GoodsProp> getPropsById(Long id) {
        List<GoodsPropPO> propList = goodsPropMapper.listByGoods(id);
        return GoodsAssembler.INSTANCE.toPropDoList(propList);
    }
}

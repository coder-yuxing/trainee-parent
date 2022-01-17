package com.yuxing.trainee.test.init;

import cn.hutool.core.collection.CollUtil;
import com.yuxing.trainee.search.api.goods.command.SaveGoodsDocCommand;
import com.yuxing.trainee.test.TraineeTestApplication;
import com.yuxing.trainee.test.domain.entity.Goods;
import com.yuxing.trainee.test.domain.repository.GoodsRepository;
import com.yuxing.trainee.test.domain.valueobject.GoodsProp;
import com.yuxing.trainee.test.infrastructure.dao.mapper.GoodsMapper;
import com.yuxing.trainee.test.infrastructure.feign.SearchFeignService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 素材es文档数据初始化
 *
 * @author yuxing
 * @since 2022/1/17
 */
@SpringBootTest(classes = TraineeTestApplication.class)
public class GoodsEsDocInit {

    @Autowired
    private SearchFeignService searchFeignService;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsMapper goodsMapper;

    @Test
    public void init() {
        List<Long> goodsIds = goodsMapper.listAll();
        goodsIds.parallelStream().forEach(goodsId -> {
            Goods goods = goodsRepository.getById(goodsId);
            List<GoodsProp> goodsProps = goodsRepository.getPropsById(goodsId);
            SaveGoodsDocCommand command = new SaveGoodsDocCommand();
            command.setId(goods.getId());
            command.setName(goods.getName());
            command.setCode(goods.getCode());
            command.setType(goods.getType().code);
            command.setCategoryId(goods.getCategoryId());
            command.setEnabled(goods.getEnabled());

            if (CollUtil.isNotEmpty(goodsProps)) {
                Map<String, List<GoodsProp>> map = goodsProps.stream().collect(Collectors.groupingBy(GoodsProp::getProp));
                List<SaveGoodsDocCommand.Prop> collect1 = map.entrySet().stream().map(e -> {
                    SaveGoodsDocCommand.Prop prop = new SaveGoodsDocCommand.Prop();
                    prop.setName(e.getKey());
                    List<GoodsProp> value = e.getValue();
                    GoodsProp v = value.get(0);
                    prop.setShowType(v.getShowType().code);
                    List<String> collect = value.stream().map(GoodsProp::getPropValue).collect(Collectors.toList());
                    prop.setValues(collect);
                    return prop;
                }).collect(Collectors.toList());
                command.setProps(collect1);
            }
            searchFeignService.save(command);
        });
    }



}

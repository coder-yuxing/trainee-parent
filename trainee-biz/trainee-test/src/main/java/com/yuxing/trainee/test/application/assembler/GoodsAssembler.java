package com.yuxing.trainee.test.application.assembler;

import cn.hutool.core.collection.CollUtil;
import com.yuxing.trainee.search.api.goods.command.BatchSaveGoodsDocCommand;
import com.yuxing.trainee.test.domain.entity.Goods;
import com.yuxing.trainee.test.domain.valueobject.GoodsProp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yuxing
 * @since 2022/1/18
 */
@Mapper
public interface GoodsAssembler {

    GoodsAssembler INSTANCE = Mappers.getMapper(GoodsAssembler.class);

    @Mapping(target = "type", expression = "java(goods.getType().code)")
    BatchSaveGoodsDocCommand.Item toSaveCommandItem(Goods goods, List<GoodsProp> props);

    default List<BatchSaveGoodsDocCommand.Item.Prop> toProps(List<GoodsProp> props) {
        if (CollUtil.isEmpty(props)) {
            return Collections.emptyList();
        }
        Map<String, List<GoodsProp>> collect = props.stream().collect(Collectors.groupingBy(GoodsProp::getProp));
        return collect.entrySet().stream().map(e -> {
            BatchSaveGoodsDocCommand.Item.Prop prop = new BatchSaveGoodsDocCommand.Item.Prop();
            prop.setName(e.getKey());
            List<GoodsProp> value = e.getValue();
            GoodsProp goodsProp = value.get(0);
            prop.setShowType(goodsProp.getShowType().code);
            prop.setValues(value.stream().map(GoodsProp::getPropValue).collect(Collectors.toList()));
            return prop;
        }).collect(Collectors.toList());
    }
}

package com.yuxing.trainee.test.infrastructure.repository.assembler;


import com.yuxing.trainee.test.domain.entity.Goods;
import com.yuxing.trainee.test.domain.valueobject.GoodsProp;
import com.yuxing.trainee.test.infrastructure.dao.model.GoodsPO;
import com.yuxing.trainee.test.infrastructure.dao.model.GoodsPropPO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author yuxing
 * @since 2022/1/17
 */
@Mapper
public interface GoodsAssembler {

    GoodsAssembler INSTANCE = Mappers.getMapper(GoodsAssembler.class);

    Goods toDo(GoodsPO po);

    GoodsPO toPo(Goods goods);

    default Goods.Type type(Integer type) {
        return Goods.Type.getByCode(type).orElse(Goods.Type.MAIN_MATERIAL);
    }

    default Integer toTypeCode(Goods.Type type) {
        if (type == null) {
            return null;
        }
        return type.code;
    }


    List<GoodsPropPO> toPropPoList(List<GoodsProp> props, @Context Long goodsId);

    @Mapping(target = "goodsId", expression = "java(goodsId)")
    GoodsPropPO toPropPo(GoodsProp prop, @Context Long goodsId);

    List<GoodsProp> toPropDoList(List<GoodsPropPO> propList);

    GoodsProp toPropDo(GoodsPropPO po);

    default GoodsProp.ShowType showType(Integer code) {
        return GoodsProp.ShowType.getByCode(code).orElse(GoodsProp.ShowType.SINGLE_CHOICE);
    }

    default Integer toShowTypeCode(GoodsProp.ShowType showType) {
        if (showType == null) {
            return null;
        }
        return showType.code;
    }
}

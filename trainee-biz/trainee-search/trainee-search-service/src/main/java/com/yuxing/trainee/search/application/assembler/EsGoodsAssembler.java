package com.yuxing.trainee.search.application.assembler;

import com.yuxing.trainee.search.api.goods.command.SaveGoodsDocCommand;
import com.yuxing.trainee.search.api.goods.dto.EsGoodsDTO;
import com.yuxing.trainee.search.api.goods.query.EsGoodsQuery;
import com.yuxing.trainee.search.domain.entity.EsGoods;
import com.yuxing.trainee.search.domain.entity.EsGoodsSearchQuery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author yuxing
 * @since 2022/1/15
 */
@Mapper
public interface EsGoodsAssembler {

    EsGoodsAssembler INSTANCE = Mappers.getMapper(EsGoodsAssembler.class);

    EsGoods toDo(SaveGoodsDocCommand command);

    @Mapping(target = "id", expression = "java(Long.parseLong(esGoods.getId()))")
    EsGoodsDTO toDto(EsGoods esGoods);

    @Mapping(target = "page", expression = "java(query.getPosition())")
    EsGoodsSearchQuery toQuery(EsGoodsQuery query);
}

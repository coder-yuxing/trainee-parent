package com.yuxing.trainee.search.application.assembler;

import com.yuxing.trainee.search.api.dto.EsGoodsDTO;
import com.yuxing.trainee.search.domain.entity.EsGoods;
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

    @Mapping(target = "id", expression = "java(Long.parseLong(esGoods.getId()))")
    EsGoodsDTO toDto(EsGoods esGoods);
}

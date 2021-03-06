package com.yuxing.trainee.search.api.goods.dto;

import lombok.Data;

/**
 * 素材文档DTO
 *
 * @author yuxing
 * @since 2022/1/15
 */
@Data
public class EsGoodsDTO {

    /**
     * 素材ID
     */
    private Long id;

    /**
     * 素材名称
     */
    private String name;

    /**
     * 素材编码
     */
    private String code;

    /**
     * 品类ID
     */
    private Long categoryId;

    /**
     * 启停用装填
     */
    private Boolean enabled;
}

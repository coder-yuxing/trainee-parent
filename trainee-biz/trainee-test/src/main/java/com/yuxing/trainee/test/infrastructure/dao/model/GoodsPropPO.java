package com.yuxing.trainee.test.infrastructure.dao.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 素材属性属性值
 *
 * @author yuxing
 * @since 2022/01/17
 */
@Getter
@Setter
@ToString
public class GoodsPropPO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 
     */
    private Long id;

    /**
     * 素材ID
     */
    private Long goodsId;

    /**
     * 属性
     */
    private String prop;

    /**
     * 属性值
     */
    private String propValue;

    /**
     * 0 单选 1 多选 2 输入
     */
    private Integer showType;
}
package com.yuxing.trainee.test.infrastructure.dao.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
 * 素材
 *
 * @author yuxing
 * @since 2022/01/17
 */
@Getter
@Setter
@ToString
public class GoodsPO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 
     */
    private Long id;

    /**
     * 素材名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 1 主材 2 辅材 3 软装
     */
    private Integer type;

    /**
     * 品类ID
     */
    private Long categoryId;

    /**
     * 启停用：1 启用 0 停用
     */
    private Boolean enabled;

    /**
     * 是否已删除：1 已删除 0 未删除
     */
    private Boolean deleted;

    /**
     * 创建人ID
     */
    private Long createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后编辑人ID
     */
    private Long updateUser;

    /**
     * 最后编辑时间
     */
    private Date updateTime;
}
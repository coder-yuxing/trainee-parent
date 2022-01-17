package com.yuxing.trainee.test.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

/**
 * 素材实体类
 *
 * @author yuxing
 * @since 2022/1/17
 */
@Data
public class Goods {

    /**
     * ID
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
    private Type type;

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

    @AllArgsConstructor
    public enum Type {

        /**
         * 主材
         */
        MAIN_MATERIAL(1),

        /**
         * 辅材
         */
        AUXILIARY(2),

        /**
         * 软装
         */
        SOFT_OUTFIT(3),

        ;
        public final int code;

        public static Optional<Type> getByCode(Integer code) {
            return Arrays.stream(Type.values()).filter(t -> t.code == code).findFirst();
        }
    }
}

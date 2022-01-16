package com.yuxing.trainee.search.api.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 创建素材文档命令
 *
 * @author yuxing
 * @since 2022/1/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveGoodsDocCommand {

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
     * 素材类型
     */
    private Integer type;

    /**
     * 品类ID
     */
    private Long categoryId;

    /**
     * 启停用
     */
    private Boolean enabled;

    /**
     * 属性属性值
     */
    private List<Prop> props;

    @Data
    public static class Prop {

        /**
         * 属性ID
         */
        private Long id;

        /**
         * 展示类型
         */
        private Integer showType;

        /**
         * 属性值
         */
        private List<String> values;

    }

}

package com.yuxing.trainee.search.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * 素材文档数据结构映射对像
 *
 * @author yuxing
 * @since 2022/1/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
// indexName 支持SpEL表达式
@Document(indexName = "#{@searchIndexConfig.getGoods()}")
public class EsGoods {

    /**
     * 文档ID
     */
    @Id
    private String id;

    /**
     * 素材名称
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;

    /**
     * 素材编码
     */
    @Field(type = FieldType.Keyword)
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
     * 素材属性值
     */
    @Field(type = FieldType.Nested)
    private List<EsProp> props;

    /**
     * 启停用
     */
    private Boolean enabled;

    @Data
    public static class EsProp {

        /**
         * 属性ID
         */
        @Field(type = FieldType.Keyword)
        private String name;

        /**
         * 展示类型
         */
        private Integer showType;

        /**
         * 属性值
         */
        @Field(type = FieldType.Keyword)
        private List<String> values;

    }

}

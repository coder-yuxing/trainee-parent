package com.yuxing.trainee.search.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

/**
 * 素材文档数据结构映射对像
 * -
 *
 * @author yuxing
 * @since 2022/1/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "#{@searchIndexConfig.getGoods()}")
@Setting(settingPath = "elastic/es-goods-setting.json")
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
     * 模型编码
     */
    @Field(type = FieldType.Keyword)
    private String code;

}

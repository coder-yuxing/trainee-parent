package com.yuxing.trainee.search.api.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}

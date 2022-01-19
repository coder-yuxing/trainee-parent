package com.yuxing.trainee.core.util.rpn;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

/**
 * 变量类型
 *
 * @author yuxing
 */
@AllArgsConstructor
public enum QuotaVariableType implements VariableType {

    /**
     * 素材品类
     */
    CATEGORY("cat"),

    /**
     * 空间类型
     */
    SPACE("spc"),

    /**
     * 系统变量
     */
    SYSTEM("sys"),

    /**
     * 工艺
     */
    ARTIFICIAL("art"),

    /**
     * 自定义参数
     */
    CUSTOM("cus"),

    /**
     * 门洞
     */
    DOORWAY("doo"),

    /**
     * 窗户
     */
    WINDOW_OPENING("win"),

    /**
     * 素材品类
     */
    CATEGORY_PROPERTY("catPro");

    public final String code;

    public static Optional<? extends VariableType> getByCode(String code) {
        return Arrays.stream(QuotaVariableType.values()).filter(v -> v.code.equalsIgnoreCase(code)).findFirst();
    }

    @Override
    public String getCode() {
        return this.code;
    }
}

package com.yuxing.trainee.test.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.Optional;

/**
 * 素材属性
 *
 * @author yuxing
 * @since 2022/1/17
 */
@Data
public class GoodsProp {

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
    private ShowType showType;

    @AllArgsConstructor
    public enum ShowType {
        SINGLE_CHOICE(0),

        MULTIPLE_CHOICE(1),

        INPUT(2)
        ;

        public final int code;

        public static Optional<ShowType> getByCode(Integer code) {
            return Arrays.stream(ShowType.values()).filter(t -> t.code == code).findFirst();
        }
    }
}

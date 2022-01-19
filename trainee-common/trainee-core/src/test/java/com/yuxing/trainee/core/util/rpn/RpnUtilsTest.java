package com.yuxing.trainee.core.util.rpn;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.Deque;

/**
 * @author yuxing
 * @since 2021/12/16
 */
public class RpnUtilsTest {

    @Test
    public void parseTest() {
        final String expression = " cus:76 + cus:83 + cus:86 + cus:89 + ( cus:84 - cus:85 ) * 1.8 + ( cus:77 - cus:78 ) * 0.3 + ( cus:90 - cus:91 ) * 0.3 + ( cus:87 - cus:88 ) * 0.3";
        Deque<Element> parse = RpnUtils.parse(expression, new NumberOperatorSet());
        System.err.println(JSON.toJSONString(parse));

        while (!parse.isEmpty()) {
            System.err.println(JSON.toJSONString(parse.pop()));
        }
    }
}
package com.yuxing.trainee.generator.infrastructure.util;

import java.util.Locale;

/**
 * 字符串处理工具类
 *
 * @author yuxing
 */
public class StringUtils {

    private StringUtils() {
    }

    /**
     * 下划线
     */
    private static final String UNDERLINE = "_";

    /**
     * 下划线转驼峰
     *
     * @param str str
     * @return 驼峰格式字符串
     */
    public static String underline2Hump(String str) {
        if (isEmpty(str)) {
            return str;
        }

        String lowerCase = str.toLowerCase();
        String[] split = lowerCase.split(UNDERLINE);
        StringBuilder builder = new StringBuilder(str.length());
        builder.append(split[0]);

        for (int i = 1; i < split.length; i++) {
            builder.append(toUpperCaseFirstLetter(split[i]));
        }
        return builder.toString();
    }

    /**
     * 字符串首字母大写
     *
     * @param var 字符串
     * @return 首字母大写的字符串
     */
    public static String toUpperCaseFirstLetter(String var) {
        char[] chars = var.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    /**
     * 字符串首字母小写
     *
     * @param var 字符串
     * @return 首字母小写的字符串
     */
    public static String toLowerCaseFirstLetter(String var) {
        char[] chars = var.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    /**
     * 是否为空
     * @param str str
     * @return bool
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}

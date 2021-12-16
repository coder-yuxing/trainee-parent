package com.yuxing.trainee.common.core.util;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 工具类
 *
 * @author ding
 * @since 2019-03-12
 */
public class StringUtils {
    private static final Pattern HTML_START_TAG_PATTERN = Pattern.compile("<[a-zA-z]{1,9}((?!>).)*>", Pattern.CASE_INSENSITIVE);

    private static final Pattern HTML_END_TAG_PATTERN = Pattern.compile("</[a-zA-z]{1,9}>", Pattern.CASE_INSENSITIVE);

    private StringUtils() {
    }

    public static String getTextByHtml(String html) {
        if (StrUtil.isBlank(html)) {
            return StrUtil.EMPTY;
        }
        Matcher startMatcher = HTML_START_TAG_PATTERN.matcher(html);
        String s = startMatcher.replaceAll(StrUtil.EMPTY);
        Matcher endMatcher = HTML_END_TAG_PATTERN.matcher(s);
        return endMatcher.replaceAll(StrUtil.EMPTY);
    }

    public static String desensitizedPhoneNumber(String phoneNumber){
        if(StrUtil.isNotEmpty(phoneNumber)){
            phoneNumber = phoneNumber.replaceAll("(\\w{3})\\w*(\\w{3})", "$1*****$2");
        }
        return phoneNumber;
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
     * 获取字符串中的第一个数字
     *
     * @param str 字符串
     * @return 第一个数字 || 不存在 返回 -1
     */
    public static int getFirstNumber(String str) {
        if (StrUtil.isBlank(str)) {
            return -1;
        }
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        // 将输入的字符串中非数字部分用空格取代并存入一个字符串
        String string = m.replaceAll(" ").trim();
        if (StrUtil.isBlank(string)) {
            return -1;
        }
        return Integer.parseInt(string.split(" ")[0]);
    }

    /**
     * 去除字符串末尾数字
     *
     * @param str 字符串
     * @return 去除末尾数字的字符串
     */
    public static String removeEndNumber(String str) {
        if (StrUtil.isBlank(str)) {
            throw new IllegalArgumentException("Parameters are not allowed to be null");
        }
        byte[] bytes = str.getBytes();
        int pointer = bytes.length - 1;
        for (int i = bytes.length - 1; i >= 0 ; i--) {
            boolean isNumber = false;
            if (bytes[i] >= 48 && bytes[i] <= 57) {
                pointer--;
                isNumber = true;
            }
            if (!isNumber) {
                break;
            }
        }
        return new String(Arrays.copyOfRange(bytes, 0, pointer + 1));
    }

    /**
     * 判断字符串是否为数值
     *
     * @param str 字符串
     * @return true || false
     */
    public static boolean isNumeric(String str){
        if (str == null) {
            return false;
        }
        boolean startsWithDot = str.startsWith(".");
        boolean endsWithDot = str.endsWith(".");
        int dotNum = 0;
        for (int i = 0; i < str.length(); i++){
            if (!Character.isDigit(str.charAt(i))){
                if (!startsWithDot && !endsWithDot && String.valueOf(str.charAt(i)).equals(".") && dotNum == 0) {
                    ++dotNum;
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    /**
     * 是否包含中文
     *
     * @param str 字符串
     * @return bool
     */
    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}

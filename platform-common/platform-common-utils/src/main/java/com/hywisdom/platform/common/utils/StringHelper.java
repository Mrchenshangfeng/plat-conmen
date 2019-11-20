package com.hywisdom.platform.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 *
 * @author wangxz
 */
public class StringHelper extends StringUtils {

    /**
     * 字符连接符
     */
    private static final char SEPARATOR = '_';

    /**
     * 下划线字符
     */
    public static final char UNDERLINE = '_';

    /**
     * 默认编码
     */
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    /**
     * 转换为字节数组
     *
     * @param str 字符串
     * @return byte[] byte [ ]
     */
    public static byte[] getBytes(String str) {
        if (str == null) {
            throw new NullPointerException("str cannot be null");
        }
        return str.getBytes(DEFAULT_CHARSET);
    }

    /**
     * 转换为字节数组
     *
     * @param bytes 字节数组
     * @return String string
     */
    public static String toString(byte[] bytes) {
        return new String(bytes, DEFAULT_CHARSET);
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true boolean
     */
    public static boolean inString(String str, String... strs) {
        if (str != null) {
            for (String s : strs) {
                if (str.equals(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 替换掉HTML标签方法
     *
     * @param html the html
     * @return the string
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        return m.replaceAll("");
    }

    /**
     * 驼峰命名法工具
     *
     * @param s the s
     * @return String  toCamelCase("hello_world") == "helloWorld" toCapitalizeCamelCase("hello_world") == "HelloWorld" toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        String ls = s.toLowerCase();

        StringBuilder sb = new StringBuilder(ls.length());
        boolean upperCase = false;
        for (int i = 0; i < ls.length(); i++) {
            char c = ls.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     *
     * @param s the s
     * @return String  toCamelCase("hello_world") == "helloWorld" toCapitalizeCamelCase("hello_world") == "HelloWorld" toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        String cs = toCamelCase(s);
        return cs.substring(0, 1).toUpperCase() + cs.substring(1);
    }

    /**
     * 驼峰命名法工具
     *
     * @param s the s
     * @return String  toCamelCase("hello_world") == "helloWorld" toCapitalizeCamelCase("hello_world") == "HelloWorld" toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * Trim to default string.
     *
     * @param str          字符串
     * @param defaultValue 默认值
     * @return String string
     */
    public static String trimToDefault(final String str, String defaultValue) {
        final String ts = trim(str);
        return isEmpty(ts) ? defaultValue : ts;
    }

    // ---------------------------------------------------------

    // --------------------- 3. --------------------------------
    /**
     * 正则匹配，只匹配一轮<br>
     * 如：abcdabfie 匹配 (ab). 表达式最终结果为 {abc,ab}
     *
     * @param s
     *            待匹配字符串
     * @param pattern
     *            正则表达式
     * @return
     */
    public static String[] match(String s, String pattern) {
        Matcher m = Pattern.compile(pattern).matcher(s);

        while (m.find()) {
            int n = m.groupCount();
            String[] ss = new String[n + 1];
            for (int i = 0; i <= n; i++) {
                ss[i] = m.group(i);
            }
            return ss;
        }
        return null;
    }

    /**
     * 正则匹配，匹配所有的正则<br>
     * 如：abcdabfie 匹配 (ab). 表达式最终结果为 [{abc,ab},{abf,ab}]
     *
     * @param s
     * @param pattern
     * @return
     */
    public static List<String[]> matchAll(String s, String pattern) {
        Matcher m = Pattern.compile(pattern).matcher(s);
        List<String[]> result = new ArrayList<String[]>(10);

        while (m.find()) {
            int n = m.groupCount();
            String[] ss = new String[n + 1];
            for (int i = 0; i <= n; i++) {
                ss[i] = m.group(i);
            }
            result.add(ss);
        }
        return result;
    }

    /**
     * 正则匹配，匹配所有的正则<br>
     * 如：abcdabfie 匹配 (ab). startIndex为2 表达式最终结果为 {abf,ab}
     *
     * @param s
     * @param pattern
     * @param startIndex
     * @return
     */
    public static String[] matchFromIndex(String s, String pattern,
                                          int startIndex) {
        Matcher m = Pattern.compile(pattern).matcher(s);

        if (m.find(startIndex)) {
            int n = m.groupCount();
            String[] ss = new String[n + 1];
            for (int i = 0; i <= n; i++) {
                ss[i] = m.group(i);
            }
            return ss;
        }
        return null;
    }

    /**
     * 正则匹配，只匹配一轮，忽略大小写<br>
     * 如：abcdabfie 匹配 (ab). 表达式最终结果为 {abc,ab}
     *
     * @param s
     *            待匹配字符串
     * @param pattern
     *            正则表达式
     * @return
     */
    public static String[] matchWeak(String s, String pattern) {
        Matcher m = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(
                s);

        while (m.find()) {
            int n = m.groupCount();
            String[] ss = new String[n + 1];
            for (int i = 0; i <= n; i++) {
                ss[i] = m.group(i);
            }
            return ss;
        }
        return null;
    }

    public static String camelToUnderline(String param) {
        if (isEmpty(param)) {
            return EMPTY;
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append(UNDERLINE);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }


    /**
     * 若str字符串已tag结束则剔除tag
     * @param str 待剔除的字符串
     * @param tag 要剔除的标签
     * @return 剔除后的字符串
     * @throws Exception
     */
    public static String trimEnd(String str,String tag) throws Exception {
        String result = str;
        if(str == null || str.equals("")){
            return str;
        }
        if(tag == null || tag.equals("")){
            throw new Exception("参数tag 不能为null或‘’ ");
        }

        int tagPosition = str.lastIndexOf(tag);
        if(tagPosition+tag.length() == str.length()){
            result = str.trim().substring(0,tagPosition);
        }
        return result;
    }
    
    public static String getObjectName(Class<?> clazz){
    	return clazz.getName();
    }
}

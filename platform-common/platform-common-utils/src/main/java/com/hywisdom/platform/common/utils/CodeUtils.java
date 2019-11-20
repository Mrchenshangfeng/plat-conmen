package com.hywisdom.platform.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CodeUtils
 *
 * @author wangxz
 * @create 2018/10/18
 *  * @since 1.0.0
 */
public class CodeUtils {


    private CodeUtils() {
    }

    // code
    public static final String SUCCESS_CODE = "200"; // 成功
    public static final String BAD_REQUEST = "400"; //Bad Request
    public static final String EMPTY_DATA = "0000";


    // message
    public static final String SUCCESS_MSG = "success"; // 成功
    public static final String DATA_WRONGFULNESS_MSG = "数据不合法"; // 数据不合法
    public static final String EXCEPTION_MSG = "系统异常"; // 异常
    public static final String EMPTY_DATA_MEG = "没有获取到数据";

    /**
     * 校验ip是否合法
     *
     * @param ipAddress
     * @return
     */
    public static boolean isIpv4(String ipAddress) {

        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." + "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();

    }
}

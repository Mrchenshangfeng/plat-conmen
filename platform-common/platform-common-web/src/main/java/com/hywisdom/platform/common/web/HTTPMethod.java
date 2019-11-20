package com.hywisdom.platform.common.web;

import com.hywisdom.platform.common.web.exception.TomcatEnumException;

/**
 * 〈功能简述〉<br>
 * 〈http方法枚举〉
 *
 * @author wangxz
 * @create 2018/11/5
 * @since 1.0.0
 */
public enum HTTPMethod {
    GET, POST, PUT, DELETE, PATCH, TRACE, HEAD, OPTIONS;

    /**
     * <p>
     * 获取HTTPMethodEnum
     * </p>
     *
     * @param method 数据库类型字符串
     * @return
     */
    public static HTTPMethod getHttpMethod(String method) {
        HTTPMethod[] httpMethods = HTTPMethod.values();
        for (HTTPMethod httpMethod : httpMethods) {
            if (httpMethod.name().equalsIgnoreCase(method)) {
                return httpMethod;
            }
        }
        throw new TomcatEnumException("Error: Unknown HTTPMethod, or do not support changing HTTPMethod!\n");
    }
}

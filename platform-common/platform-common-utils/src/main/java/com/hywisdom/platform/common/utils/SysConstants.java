package com.hywisdom.platform.common.utils;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 常量表
 *
 */
public class SysConstants {

    public static final String Exception_Head = "系统出错了,";
    /**
     * 缓存键值
     */
    public static final Map<Class<?>, String> cacheKeyMap = Maps.newHashMap();

    /**
     * 缓存命名空间
     */
    public static final String CACHE_NAMESPACE = "HYWISDOM:";
}

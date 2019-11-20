package com.hywisdom.platform.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *  * 〈一句话功能简述〉<br>
 * 〈取类class定义〉
 *
 * @author wangxz
 * @create 2018/10/18
 * @since 1.0.0
 */
public class GenericeClassUtils {
    @SuppressWarnings("rawtypes")
    public static Class getSuperClassGenricType(Class clazz, int index) {

        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return null;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return null;
        }

        if (!(params[index] instanceof Class)) {
            return null;
        }
        return (Class) params[index];
    }
}

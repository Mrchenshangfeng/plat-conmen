package com.hywisdom.platform.common.model.convert;

import com.hywisdom.platform.common.utils.BeanConverter;

import java.io.Serializable;

/**
 * 〈功能简述〉<br>
 * 〈普通实体父类〉
 *
 * @author wangxz
 * @create 2018/11/5
 * @since 1.0.0
 */
public class Convert implements Serializable {

    /**
     * 获取自动转换后的JavaBean对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T convert(Class<T> clazz) {
        return BeanConverter.convert(clazz, this);
    }
}

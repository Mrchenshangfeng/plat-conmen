package com.hywisdom.platform.common.service.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import java.io.Serializable;

/**
 * 〈功能简述〉<br>
 * 〈Wrapper构造器〉
 *
 * @author wangxz
 * @create 2018/11/5
 * @since 1.0.0
 */
public class Wrappers implements Serializable {

    private static final QueryWrapper queryEmptyWrapper = new QueryEmptyWrapper<>();

    public static <T> QueryWrapper<T> query() {
        return new QueryWrapper<>();
    }

    public static <T> UpdateWrapper<T> update() {
        return new UpdateWrapper<>();
    }

    public static <T> QueryWrapper<T> query(T entity) {
        return new QueryWrapper<>(entity);
    }

    public static <T> UpdateWrapper<T> update(T entity) {
        return new UpdateWrapper<>(entity);
    }

    public static <T> QueryWrapper<T> emptyQueryWrapper() {
        return (QueryWrapper<T>) queryEmptyWrapper;
    }
}

package com.hywisdom.platform.common.service.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hywisdom.platform.common.model.convert.Convert;
import com.hywisdom.platform.common.service.mybatisplus.Wrappers;

import java.util.List;
import java.util.Map;

/**
 * 〈功能简述〉<br>
 * 〈基类Service继承mybatis-plus〉
 *
 * @author wangxz
 * @create 2018/11/5
 * @since 1.0.0
 */
public interface BaseService<T extends Convert> extends IService<T> {

    /**
     * <p>
     * 查询列表
     * </p>
     */
    default List<T> list() {
        return list(Wrappers.emptyQueryWrapper());
    }

    /**
     * <p>
     * 翻页查询自定义对象
     * </p>
     *
     * @param page    翻页对象
     * @param wrapper {@link Wrapper}
     * @param cls
     * @return
     */
    <E> IPage<E> pageEntities(IPage page, QueryWrapper wrapper, Class<E> cls);

    /**
     * <p>
     * 根据 Wrapper，查询一条自定义对象记录
     * </p>
     *
     * @param wrapper {@link Wrapper}
     * @param cls
     * @return
     */
    <E extends Convert> E entity(QueryWrapper wrapper, Class<E> cls);

    /**
     * <p>
     * 查询自定义对象列表
     * </p>
     *
     * @param wrapper {@link Wrapper}
     * @param cls
     * @return
     */
    <E extends Convert> List<E> entitys(QueryWrapper wrapper, Class<E> cls);

    /**
     * 以list中对象的某个属性做键值,转换成map
     * <p>
     *
     * @param list     要转换的list
     * @param property list中对象的属性,作为键值
     * @return 转换后的map
     */
    <V> Map<Integer, V> list2Map(List<V> list, String property);

    /**
     * 查询list,使用list中对象的某个属性做键值,转换成map
     * <p>
     *
     * @param wrapper  条件
     * @param property list中对象的属性,作为键值
     * @return 转换后的map
     */
    Map<Integer, T> list2Map(QueryWrapper<T> wrapper, String property);
}

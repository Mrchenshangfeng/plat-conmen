package com.hywisdom.platform.common.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hywisdom.platform.common.model.convert.Convert;
import com.hywisdom.platform.common.service.dao.SuperMapper;
import com.hywisdom.platform.common.service.service.BaseService;
import com.hywisdom.platform.common.utils.BeanConverter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 〈功能简述〉<br>
 * 〈基础service实现，继承mybatis-plus〉
 *
 * @author wangxz
 * @create 2018/11/5
 * @since 1.0.0
 */
@Transactional(readOnly = true)
public class BaseServiceImpl<M extends BaseMapper<T>,T extends Convert>
        extends ServiceImpl<M,T> implements BaseService<T> {

    @Autowired
    protected M baseMapper;

    @Override
    public <E> IPage<E> pageEntities(IPage page, QueryWrapper wrapper, Class<E> cls) {
        page(page, wrapper);
        page.setRecords(BeanConverter.convert(cls, page.getRecords()));
        return page;
    }

    @Override
    public <E extends Convert> E entity(QueryWrapper wrapper, Class<E> cls) {
        E entity = null;
        T t = (T) getOne(wrapper);
        if (Objects.nonNull(t)) {
            entity = t.convert(cls);
        }
        return entity;
    }

    @Override
    public <E extends Convert> List<E> entitys(QueryWrapper wrapper, Class<E> cls) {
        List<E> entitys = Collections.emptyList();
        List<T> list = list(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            entitys = BeanConverter.convert(cls, list);
        }
        return entitys;
    }


    @Override
    public <V> Map<Integer, V> list2Map(List<V> list, String property) {
        if (list == null) {
            return Collections.emptyMap();
        }
        Map<Integer, V> map = new LinkedHashMap<>(list.size());
        for (V v : list) {
            Field field = ReflectionUtils.findField(v.getClass(), property);
            ReflectionUtils.makeAccessible(field);
            Object fieldValue = ReflectionUtils.getField(field, v);
            map.put((Integer) fieldValue, v);
        }
        return map;
    }

    @Override
    public Map<Integer, T> list2Map(QueryWrapper<T> wrapper, String property) {
        return list2Map(list(wrapper), property);
    }
}

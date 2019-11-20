package com.hywisdom.platform.common.utils;

import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.hywisdom.platform.common.utils.modelmapper.jdk8.Jdk8Module;
import com.hywisdom.platform.common.utils.modelmapper.jsr310.Jsr310Module;
import com.hywisdom.platform.common.utils.modelmapper.jsr310.Jsr310ModuleConfig;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.beans.BeanMap;

import java.time.ZoneOffset;
import java.util.*;

/**
 * 〈功能简述〉<br>
 * 〈转换工具类〉
 *
 * @author wangxz
 * @create 2018/11/5
 * @since 1.0.0
 */
public class BeanConverter {
    private static final ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        Jsr310ModuleConfig config = Jsr310ModuleConfig.builder()
                .dateTimePattern("yyyy-MM-dd HH:mm:ss") // default is yyyy-MM-dd HH:mm:ss
                .datePattern("yyyy-MM-dd") // default is yyyy-MM-dd
                .zoneId(ZoneOffset.UTC) // default is ZoneId.systemDefault()
                .build();
        modelMapper.registerModule(new Jsr310Module(config)).registerModule(new Jdk8Module());
    }

    /**
     * 获取 modelMapper
     *
     * @return
     */
    public static ModelMapper getModelMapper() {
        return modelMapper;
    }

    /**
     * Bean转换为Map
     *
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Collections.emptyMap();
        if (null != bean) {
            BeanMap beanMap = BeanMap.create(bean);
            map = new HashMap<>(beanMap.keySet().size());
            for (Object key : beanMap.keySet()) {
                map.put(String.valueOf(key), beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * List<E>转换为List<Map<String, Object>>
     *
     * @param objList
     * @param <T>
     * @return
     */
    public static <T> List<Map<String, Object>> beansToMap(List<T> objList) {
        List<Map<String, Object>> list = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(objList)) {
            list = new ArrayList<>(objList.size());
            Map<String, Object> map;
            T bean;
            for (T anObjList : objList) {
                bean = anObjList;
                map = beanToMap(bean);
                list.add(map);
            }
        }
        return list;
    }


    /**
     * map转为bean
     *
     * @param <T>       the type parameter
     * @param mapList   the map list
     * @param beanClass the bean class
     * @return t list
     */
    public static <T> List<T> mapToBean(List<Map<String, Object>> mapList, Class<T> beanClass) {
        List<T> list = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(mapList)) {
            list = new ArrayList<>(mapList.size());
            Map<String, Object> map;
            T bean;
            for (Map<String, Object> map1 : mapList) {
                map = map1;
                bean = mapToBean(map, beanClass);
                list.add(bean);
            }
        }
        return list;
    }


    /**
     * map转为bean
     *
     * @param map       the map
     * @param beanClass the bean class
     * @return t
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass) {
        T entity = ClassUtils.newInstance(beanClass);
        BeanMap beanMap = BeanMap.create(entity);
        beanMap.putAll(map);
        return entity;
    }

    /**
     * 列表转换
     *
     * @param clazz the clazz
     * @param list  the list
     * @return the page list
     */
    public static <T> List<T> convert(Class<T> clazz, List<?> list) {
        //返回的list列表
        List<T> resultList = Collections.emptyList();
        if (CollectionUtils.isEmpty(list)) {
            return resultList;
        }
        resultList = new ArrayList<>(list.size());
        Iterator<?> iterator = list.iterator();
        //循环调用转换单个对象
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            T t = convert(clazz, obj);
            resultList.add(t);
        }
        return resultList;
    }

    /**
     * 单个对象转换
     *
     * @param targetClass 目标对象
     * @param source      源对象
     * @return 转换后的目标对象
     */
    public static <T> T convert(Class<T> targetClass, Object source) {
        return getModelMapper().map(source, targetClass);
    }

}

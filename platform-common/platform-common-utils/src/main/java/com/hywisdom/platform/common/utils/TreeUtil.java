package com.hywisdom.platform.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: hyzl-authentication
 * @description:
 * @author: xfl
 * @CreateTime: 2018年07月23日 14:08
 **/
public class TreeUtil {
    /**
     * @Method createTree
     * @Description: 创建树
     * @param list               树数据
     * @param root               根节点
     * @param keyFieldName       关联属性
     * @param parentKeyFieldName 关联父属性
     * @param subFieldName       子节点数据
     * @return：void <T>         根节点
     * @exception
     * @Author: xfl
     * @Date: 2018/7/23
    **/
    public static <T> void createTree(List<T> list, T root, String keyFieldName, String parentKeyFieldName, String subFieldName) {
        Field keyField = ReflectUtil.getField(keyFieldName, root);
        Field parentKeyField = ReflectUtil.getField(parentKeyFieldName, root);
        Field subField = ReflectUtil.getField(subFieldName, root);
        find(list, root, keyField, parentKeyField, subField);
    }

    /**
     * 根据父节点的关联值 查找
     */
    public static <T, E> List<E> getKeys(List<T> list, T root, String keyFieldName, String parentKeyFieldName) {
        Field keyField = ReflectUtil.getField(keyFieldName, root);
        Field parentKeyField = ReflectUtil.getField(parentKeyFieldName, root);
        List<E> keys = new ArrayList<>();
        E value = ReflectUtil.getValueByGetMethod(keyField, root);
        keys.add(value);
        findKeys(list, keys, root, keyField, parentKeyField);
        return keys;
    }

    private static <T> void find(List<T> list, T parent, Field keyField, Field parentKeyField, Field subField) {
        List<T> subs = getSubs(list, parent, keyField, parentKeyField);

        if (subs != null) {
            ReflectUtil.setValueByField(subField, parent, subs);
            for (T sub : subs) {
                //递归找子节点
                find(list, sub, keyField, parentKeyField, subField);
            }
        }
    }

    private static <T, E> List<E> findKeys(List<T> list, List<E> keys, T parent, Field keyField, Field parentKeyField) {
        List<T> subs = getSubs(list, parent, keyField, parentKeyField);

        List<E> subKeys = getSubKeys(list, parent, keyField, parentKeyField);
        if (subs != null) {
            keys.addAll(subKeys);
            for (T sub : subs) {
                //递归找子节点
                findKeys(list, keys, sub, keyField, parentKeyField);
            }
        }
        return keys;
    }


    private static <T> List<T> getSubs(List<T> list, T parent, Field keyField, Field parentKeyField) {
        List<T> subs = null;
        for (T t : list) {
            Object keyFieldValue = ReflectUtil.getValueByField(keyField, parent);
            Object parentFieldValue = ReflectUtil.getValueByField(parentKeyField, t);
            if (keyFieldValue.equals(parentFieldValue)) {
                if (subs == null) {
                    subs = new ArrayList<>();
                }
                subs.add(t);
            }
        }
        return subs;
    }


    private static <T, E> List<E> getSubKeys(List<T> list, T parent, Field keyField, Field parentKeyField) {
        List<E> subs = null;
        for (T t : list) {
            Object keyFieldValue = ReflectUtil.getValueByField(keyField, parent); //父节点key
            Object parentFieldValue = ReflectUtil.getValueByField(parentKeyField, t); //根结点关联的key
            if (keyFieldValue.equals(parentFieldValue)) { //关联字段相等
                if (subs == null) {
                    subs = new ArrayList<>();
                }
                //取子节点key
                Object key = ReflectUtil.getValueByField(keyField, t);
                subs.add((E) key);
            }
        }
        return subs;
    }
}

package com.hywisdom.platform.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 实例辅助类
 */
public final class InstanceUtil {
    private InstanceUtil() {
    }

    /**
     * 实例化并复制属性
     */
    public static final <T> T to(Object orig, Class<T> clazz) {
        T bean = null;
        try {
            bean = clazz.newInstance();
            PropertyUtils.copyProperties(bean, orig);
        } catch (Exception e) {
        }
        return bean;
    }

    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
    public static void transMap2Bean(Map<String, Object> map, Object obj) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }
            }
        } catch (Exception e) {
            System.out.println("transMap2Bean Error " + e);
        }
        return;
    }

    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static Map<String, Object> transBean2Map(Object obj) {
        Map<String, Object> map = Maps.newHashMap();
        if (obj == null) {
            return map;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
        return map;
    }


    /**
     * Return the specified class. Checks the ThreadContext classloader first,
     * then uses the System classloader. Should replace all calls to
     * <code>Class.forName( claz )</code> (which only calls the System class
     * loader) when the class might be in a different classloader (e.g. in a
     * webapp).
     *
     * @param clazz the name of the class to instantiate
     * @return the requested Class object
     */
    public static final Class<?> getClass(String clazz) {
        /**
         * Use the Thread context classloader if possible
         */
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            if (loader != null) {
                return Class.forName(clazz, true, loader);
            }
            /**
             * Thread context classloader isn't working out, so use system
             * loader.
             */
            return Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            throw new InstanceException(e);
        }
    }

    /**
     * 封装实体
     *
     * @param cls  实体类
     * @param list 实体Map集合
     * @return
     */
    public static final <E> List<E> getInstanceList(Class<E> cls, List<?> list) {
        List<E> resultList = Lists.newArrayList();
        E object = null;
        for (Iterator<?> iterator = list.iterator(); iterator.hasNext(); ) {
            Map<?, ?> map = (Map<?, ?>) iterator.next();
            object = newInstance(cls, map);
            resultList.add(object);
        }
        return resultList;
    }

    /**
     * 封装实体
     *
     * @param cls 实体类
     * @return
     */
    public static final <E> List<E> getInstanceList(Class<E> cls, ResultSet rs) {
        List<E> resultList = Lists.newArrayList();
        try {
            E object = cls.newInstance();
            Field[] fields = cls.getDeclaredFields();
            while (rs.next()) {
                object = cls.newInstance();
                for (int i = 0; i < fields.length; i++) {
                    String fieldName = fields[i].getName();
                    PropertyUtils.setProperty(object, fieldName, rs.getObject(fieldName));
                }
                resultList.add(object);
            }
        } catch (Exception e) {
            throw new InstanceException(e);
        }
        return resultList;
    }

    /**
     * 新建实例
     *
     * @param cls 实体类
     * @return
     */
    public static final <E> E newInstance(Class<E> cls, Map<String, ?> map) {
        E object = null;
        try {
            object = cls.newInstance();
            BeanUtils.populate(object, map);
        } catch (Exception e) {
            throw new InstanceException(e);
        }
        return object;
    }

    /**
     * Return a new instance of the given class. Checks the ThreadContext
     * classloader first, then uses the System classloader. Should replace all
     * calls to <code>Class.forName( claz ).newInstance()</code> (which only
     * calls the System class loader) when the class might be in a different
     * classloader (e.g. in a webapp).
     *
     * @param clazz the name of the class to instantiate
     * @return an instance of the specified class
     */
    public static final Object newInstance(String clazz) {
        try {
            return getClass(clazz).newInstance();
        } catch (Exception e) {
            throw new InstanceException(e);
        }
    }

    public static final <K> K newInstance(Class<K> cls, Object... args) {
        try {
            Class<?>[] argsClass = null;
            if (args != null) {
                argsClass = new Class[args.length];
                for (int i = 0, j = args.length; i < j; i++) {
                    argsClass[i] = args[i].getClass();
                }
            }
            Constructor<K> cons = cls.getConstructor(argsClass);
            return cons.newInstance(args);
        } catch (Exception e) {
            throw new InstanceException(e);
        }
    }

    public static Map<String, Class<?>> clazzMap = new HashMap<String, Class<?>>();

    /**
     * 新建实例
     *
     * @param className 类名
     * @param args      构造函数的参数
     * @return 新建的实例
     */
    public static final Object newInstance(String className, Object... args) {
        try {
            Class<?> newoneClass = clazzMap.get(className);
            if (newoneClass == null) {
                newoneClass = Class.forName(className);
                clazzMap.put(className, newoneClass); // 缓存class对象
            }
            return newInstance(newoneClass, args);
        } catch (Exception e) {
            throw new InstanceException(e);
        }
    }

    public static <T> T getDiff(T oldBean, T newBean) {
        if (oldBean == null && newBean != null) {
            return newBean;
        } else if (newBean == null) {
            return null;
        } else {
            Class<?> cls1 = oldBean.getClass();
            try {
                @SuppressWarnings("unchecked")
                T object = (T) cls1.newInstance();
                BeanInfo beanInfo = Introspector.getBeanInfo(cls1);
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor property : propertyDescriptors) {
                    String key = property.getName();
                    // 过滤class属性
                    if (!key.equals("class")) {
                        // 得到property对应的getter方法
                        Method getter = property.getReadMethod();
                        // 得到property对应的setter方法
                        Method setter = property.getWriteMethod();
                        Object oldValue = getter.invoke(oldBean);
                        Object newValue = getter.invoke(newBean);
                        if (newValue != null) {
                            if (oldValue == null) {
                                setter.invoke(object, newValue);
                            } else if (oldValue != null && !newValue.equals(oldValue)) {
                                setter.invoke(object, newValue);
                            }
                        }
                    }
                }
                return object;
            } catch (Exception e) {
                throw new InstanceException(e);
            }
        }
    }


    /**
     * 获取修改属性（只取过滤的属性）
     * @param oldBean
     * @param newBean
     * @param filterProps 过滤属性
     * @return
     */
    public static <T> HashMap<String,String> getDiff(T oldBean, T newBean, List<String> filterProps) {
        HashMap<String,String> properties = new HashMap<String,String>();
        if (oldBean == null && newBean != null) {
            return null;
        } else if (newBean == null) {
            return null;
        } else {
            Class<?> cls1 = oldBean.getClass();
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(cls1);
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor property : propertyDescriptors) {
                    String key = property.getName();
                    if(filterProps.contains(key)){
                        // 过滤class属性
                        if (!key.equals("class")) {
                            // 得到property对应的getter方法
                            Method getter = property.getReadMethod();
                            // 得到property对应的setter方法
                            //Method setter = property.getWriteMethod();
                            Object oldValue = getter.invoke(oldBean);
                            Object newValue = getter.invoke(newBean);
                            if (newValue != null && newValue !="") {
                                if (oldValue == null) {
                                    properties.put(key, newValue.toString());
                                } else if (oldValue != null && !newValue.equals(oldValue)) {
                                    properties.put(key, newValue.toString());
                                }
                            }
                        }
                    }
                }
                return properties;
            } catch (Exception e) {
                throw new InstanceException(e);
            }
        }
    }


    /**
     * 获取修改属性（只取过滤的属性）
     * @param oldBean
     * @param newBean
     * @param filterProps 过滤属性
     * @return
     */
    public static <T> HashMap<String,String> getDiffProperties(T oldBean, T newBean, List<String> filterProps) {
    	HashMap<String,String> properties = new HashMap<String,String>();
        if (oldBean == null && newBean != null) {
            return null;
        } else if (newBean == null) {
            return null;
        } else {
            Class<?> cls1 = oldBean.getClass();
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(cls1);
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor property : propertyDescriptors) {
                    String key = property.getName();
                    if(filterProps.contains(key)){
                    	// 过滤class属性
                    	if (!key.equals("class")) {
                    		// 得到property对应的getter方法
                    		Method getter = property.getReadMethod();
                    		// 得到property对应的setter方法
                    		//Method setter = property.getWriteMethod();
                    		Object oldValue = getter.invoke(oldBean);
                    		Object newValue = getter.invoke(newBean);
                    		if (newValue != null) {
                    			if (oldValue == null) {
                    				properties.put(key+"_update", newValue.toString());
                    			} else if (oldValue != null && !newValue.equals(oldValue)) {
                    				properties.put(key+"_update", newValue.toString());
                    			}
                    		}
                    	}
                    }
                }
                return properties;
            } catch (Exception e) {
                throw new InstanceException(e);
            }
        }
    }
}

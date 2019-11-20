package com.hywisdom.platform.common.utils;

import java.math.BigDecimal;
import java.util.*;

/**
 * HashMap对象的一个扩展.
 *
 * @author wangxz
 * @create 2018/10/18
 *  * @since 1.0.0
 */
public class BaseDto extends HashMap<String, Object> implements Dto {

    private static final long serialVersionUID = 1L;

    public BaseDto() {
        super();
    }

    public BaseDto(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public BaseDto(int initialCapacity) {
        super(initialCapacity);
    }

    public BaseDto(Map<String, Object> m) {
        super(m);
    }

    @Override
    public String getString(String key) {
        return Objects.toString(this.get(key));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> List<E> getList(String key) {
        Object o = this.get(key);
        if (o instanceof List) {
            return (List<E>) o;
        }
        return new ArrayList<>();
    }

    @Override
    public int getInt(String key) {
        Object value = this.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        }
        return NumberHelper.toInt(Objects.toString(value));
    }

    @Override
    public long getLong(String key) {
        Object value = this.get(key);
        if (value instanceof Long) {
            return (Long) value;
        }
        return NumberHelper.toLong(Objects.toString(value));
    }

    @Override
    public double getDouble(String key) {
        Object value = this.get(key);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return NumberHelper.toDouble(Objects.toString(value));
    }

    @Override
    public BigDecimal getDecimal(String key) {
        String str = this.getString(key);
        if (StringHelper.isEmpty(str)) {
            return BigDecimal.ZERO;
        }
        return NumberHelper.createBigDecimal(str);
    }

    @Override
    public Date getDate(String key) {
        Object value = this.get(key);
        if (value instanceof Date) {
            return (Date) value;
        }
        return DateHelper.parseDate(value);
    }
}
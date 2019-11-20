package com.hywisdom.platform.common.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 〈功能简述〉<br>
 * 〈supermapper，这个类不要让 mp 扫描到〉
 *
 * @author wangxz
 * @create 2018/10/30
 * @since 1.0.0
 */
public interface SuperMapper<T> extends BaseMapper<T> {

    // 这里可以放一些公共的方法
}

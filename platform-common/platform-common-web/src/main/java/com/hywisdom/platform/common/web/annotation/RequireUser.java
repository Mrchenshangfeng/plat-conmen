package com.hywisdom.platform.common.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 〈功能简述〉<br>
 * 〈用来标注该方法需要获取登录用户ID，后续可以完善为注入用户ID等信息〉
 *
 * @author wangxz
 * @create 2018/10/18
 * @since 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface RequireUser {
}

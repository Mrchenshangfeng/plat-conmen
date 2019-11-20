package com.hywisdom.platform.common.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 〈功能简述〉<br>
 * 〈当前用户〉
 *
 * @author wangxz
 * @create 2018/10/18
 * @since 1.0.0
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {

}


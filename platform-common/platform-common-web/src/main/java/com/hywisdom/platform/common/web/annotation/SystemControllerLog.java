package com.hywisdom.platform.common.web.annotation;

import com.hywisdom.platform.common.model.constants.LogEnum;
import com.hywisdom.platform.common.web.HTTPMethod;

import java.lang.annotation.*;

/**
 * 〈功能简述〉<br>
 * 〈controller日志记录〉
 *
 * @author wangxz
 * @create 2019/3/8
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)         //元注解，定义注解被保留策略，一般有三种策略，
@Target({ElementType.METHOD})               //定义了注解声明在哪些元素之前
@Documented
public @interface SystemControllerLog {

    LogEnum module();//模块名称

    //定义成员
    String description() default "";        //描述

    HTTPMethod actionType() default HTTPMethod.GET;         //操作的类型，1、POST 2、PUT 3、DELETE 4、GET
}

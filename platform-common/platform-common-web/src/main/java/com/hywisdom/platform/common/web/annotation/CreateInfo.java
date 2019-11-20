package com.hywisdom.platform.common.web.annotation;

import java.lang.annotation.*;

/**
 * 创建信息的注解
 * @author yyh
 * @date 2019/9/18 16:53
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CreateInfo {

    String description() default "";

}

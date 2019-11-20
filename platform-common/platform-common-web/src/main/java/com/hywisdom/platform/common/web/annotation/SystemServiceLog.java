package com.hywisdom.platform.common.web.annotation;

import java.lang.annotation.*;

/**
 * 〈功能简述〉<br>
 * 〈系统服务log〉
 *
 * @author wangxz
 * @create 2019/3/8
 * @since 1.0.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SystemServiceLog {
    String description() default "";
}

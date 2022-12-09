package com.zhang.cloud.aop;

import java.lang.annotation.*;

/**
 * @author 98549
 * @date 2021/10/28 1:06
 */
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authentication {
    String value() default "";
}

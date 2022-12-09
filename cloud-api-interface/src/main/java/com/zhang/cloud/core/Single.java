package com.zhang.cloud.core;

import java.lang.annotation.*;

/**
 * @author 98549
 * @date 2021/12/24 17:27
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Single {
}

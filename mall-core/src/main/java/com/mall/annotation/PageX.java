package com.mall.annotation;

import java.lang.annotation.*;

/**
 * 分页处理，声明某方法使用分页
 *
 * @author JngKang
 * @date 2022-06-24 08:27
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PageX {
}
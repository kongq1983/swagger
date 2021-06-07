package com.kq.customize.javassist.dto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * ApiReturnJsonArray表示的是list数据结构（用于接口返回值类型声明）
 * ApiReturnJsonArray
 *
 * @author kq
 * @date 2021/6/7 22:38
 * @since 1.0.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiReturnJsonArray {
    ApiReturnJsonObject[] values();

    String name() default "ReturnList";  //类名
}

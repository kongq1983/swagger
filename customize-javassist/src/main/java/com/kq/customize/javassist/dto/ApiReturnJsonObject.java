package com.kq.customize.javassist.dto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * ApiReturnJsonObject表示map数据结构（用于接口返回值类型声明）
 * ApiReturnJsonObject
 *
 * @author kq
 * @date 2021/6/7 22:37
 * @since 1.0.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiReturnJsonObject {
    ApiJsonProperty[] value(); //对象属性值

    String name() default "ReturnMap";  //类名
}


package com.kq.customize.javassist.dto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * ApiJsonObject表示map数据结构（用于接口入参声明）
 * ApiJsonObject
 *
 * @author kq
 * @date 2021/6/7 22:39
 * @since 1.0.0
 */
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiJsonObject {

    ApiJsonProperty[] value(); //对象属性值

    String name() default "ParamMap";  //对象名
}


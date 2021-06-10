package com.kq.swagger.customize.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kq
 * @date 2021-06-10 14:23
 * @since 2020-0630
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SwaggerResponseEntity {

    /**
     * 返回多个字段
     * @return
     */
    SwaggerResponseField[] value();

    String name() default "SwaggerResponseEntity";

}

package com.kq.swagger.customize.config.annotation;

import java.lang.annotation.*;

/**
 * @author kq
 * @date 2021-06-09 9:16
 * @since 2020-0630
 */
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SwaggerResponseObject {

    /**
     * 返回多个字段
     * @return
     */
    SwaggerResponseField[] value();

    String name() default "ResponseObject";

}

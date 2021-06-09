package com.kq.swagger.customize.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kq
 * @date 2021-06-09 9:14
 * @since 2020-0630
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SwaggerResponseField {

    /**
     * 参数名称
     * */
    String name();

    /**
     * 参数的中文含义
     * */
    String value() default "";

    /**
     * 参数示例
     * */
    String example() default "";

    /**
     * 支持各种基础数据类型、或包装的数据类型
     * */
    String dataType() default "string";

    /**
     * 参数描述（想要在参数旁有中文解析，要给此属性赋值）
     * */
    String description() default "";
    /**
     * 是否必填
     * */
    boolean required() default false;

    //支持string 和 int
    Class type() default String.class;

}

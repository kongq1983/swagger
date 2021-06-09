package com.kq.swagger.customize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * SwaggerCustomizeJavasistApplication
 *
 * @author kq
 * @date 2021/6/7 23:01
 * @since 1.0.0
 */
@SpringBootApplication
public class SwaggerSelfCustomizeApplication {


    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(SwaggerSelfCustomizeApplication.class, args);

    }

}

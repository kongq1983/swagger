package com.kq.swaagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author kq
 * @date 2021-06-03 17:23
 * @since 2020-0630
 */
@SpringBootApplication
public class SwaggerApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(SwaggerApplication.class, args);

    }

}

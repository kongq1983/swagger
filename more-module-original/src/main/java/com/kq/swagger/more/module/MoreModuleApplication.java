package com.kq.swagger.more.module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author kq
 * @date 2021-06-16 11:08
 * @since 2020-0630
 */
@SpringBootApplication
public class MoreModuleApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(MoreModuleApplication.class, args);

    }

}

package com.kq.swagger.more.module.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author kq
 * @date 2021-06-16 11:13
 * @since 2020-0630
 */
@Configuration
public class SwaggerMoreModuleConfig {

    /**
     * 用户模块Api
     * @return
     */
    @Bean
    public Docket userModuleApi() {
        String moduleCode = "117";
        String moduleName = "用户模块";
        String[] basePackage = { "com.kq.swagger.more.module.controller.user"};
        return Swagger2ConfigUtils.docket(moduleCode, moduleName, basePackage);
    }


    /**
     * 产品模块Api
     * @return
     */
    @Bean
    public Docket productModuleApi() {
        String moduleCode = "118";
        String moduleName = "产品模块";
        String[] basePackage = { "com.kq.swagger.more.module.controller.product"};
        return Swagger2ConfigUtils.docket(moduleCode, moduleName, basePackage);
    }

}

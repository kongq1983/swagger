package com.kq.swaagger.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @author kq
 * @date 2021-06-03 16:55
 * @since 2020-0630
 */

@Configuration
@EnableSwagger2
@ConditionalOnExpression("${swagger.enable:false}")
public class SwaggerConfig {


    @Bean
    public Docket swaggerSpringMvcPlugin() {
        ApiInfo apiInfo = new ApiInfo(
                "Spring Boot APIs",
                "Spring Boot Swagger2",
                "1.0.0",
                null,
                "shr-parent",
                "author: king",
                "http://www.zyzh.cn/");
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(regex("/api/.*"))
                .build()
                .apiInfo(apiInfo)
                .useDefaultResponseMessages(false);
        return docket;
    }

//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
//                .excludePathPatterns("/swagger-ui.html", "/v2/**", "/swagger-resources/**");
//    }



//    public void configure ( WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers("/swagger-ui.html")
//                .antMatchers("/v2/**")
//                .antMatchers("/swagger-resources/**");
//    }


}

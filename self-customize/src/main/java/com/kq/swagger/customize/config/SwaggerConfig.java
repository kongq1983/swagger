package com.kq.swagger.customize.config;

import io.swagger.annotations.ApiOperation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


/**
 * SwaggerConfig
 *
 * @author kq
 * @date 2021/6/7 22:54
 * @since 1.0.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                //设置basePackage会将包下的所有被@Api标记类的所有方法作为api
                .apis(RequestHandlerSelectors.basePackage("com.kq.swagger.customize.controller"))
                //只有标记了@ApiOperation的方法才会暴露出给swagger
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any()).build().globalOperationParameters(getDefaultHeaders());
    }

    private ApiInfo getApiInfo(){
        return new ApiInfoBuilder()
                .title("Shr Swagger接口文档 Demo")
                .termsOfServiceUrl("http://localhost:8080/swagger-ui.html")
                .version("1.0")
                .build();
    }

    private List<Parameter>  getDefaultHeaders(){
        ParameterBuilder contentType = new ParameterBuilder();
        List<Parameter> pars = new ArrayList();
        contentType.name("content-type").description("contentType 请求类型")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        pars.add(contentType.build());  //根据每个方法名也知道当前方法在设置什么参数

        return pars;

    }

}


package com.kq.customize.javassist.plugin;

import com.fasterxml.classmate.ResolvedType;
import com.google.common.base.Optional;
import com.kq.customize.javassist.dto.ApiJsonObject;
import com.kq.customize.javassist.dto.ApiJsonProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;
import java.util.Map;


/**
 * plugin加载顺序，默认是最后加载
 * 此类用于swagger的接口参数描述
 * MyParamPlugin
 *
 * @author kq
 * @date 2021/6/7 22:53
 * @since 1.0.0
 */
@Component
@Order
public class MyParamPlugin extends MyBaseBuildPlugin implements ParameterBuilderPlugin{

    @Override
    public void apply(ParameterContext parameterContext) {
        ResolvedMethodParameter methodParameter = parameterContext.resolvedMethodParameter();
        int paramIndex = MyBaseBuildPlugin.paramIndex++;
        //判断是否需要修改对象ModelRef,这里我判断的是Map类型和String类型需要重新修改ModelRef对象
        if (methodParameter.getParameterType().canCreateSubtype(Map.class)
                || methodParameter.getParameterType().canCreateSubtype(String.class)) {
            //根据参数上的ApiJsonObject注解中的参数动态生成Class
            Optional<ApiJsonObject> optional = methodParameter.findAnnotation(ApiJsonObject.class);
            if (optional.isPresent()) {
                //model 名称
                String name = optional.get().name();
                ApiJsonProperty[] properties = optional.get().value();
                //classname要尽量弄成不一样,不然汇报frozon class的错
                String classname = name + String.valueOf(paramIndex);
                //像documentContext的Models中添加我们新生成的Class
                ResolvedType resolvedType = typeResolver.resolve(createRefModel(parameterContext.getDocumentationContext(),properties,classname));
                parameterContext.getDocumentationContext().getAdditionalModels().add(resolvedType);
                //修改Map参数的ModelRef为我们动态生成的class
                parameterContext.parameterBuilder()
                        .parameterType("body")
                        .modelRef(new ModelRef(classname))
                        .name(name);
            }
        }
    }


    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }
}


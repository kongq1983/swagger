package com.kq.swagger.customize.plugin;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.RequestMappingContext;
import springfox.documentation.spring.web.readers.operation.OperationModelsProvider;

/**
 * @see springfox.documentation.spring.web.readers.operation.OperationModelsProvider
 * @author kq
 * @date 2021-06-11 9:34
 * @since 2020-0630
 */
@Deprecated
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE+100)
public class MyResponseOperationModelProvider  extends OperationModelsProvider {// implements OperationModelsProviderPlugin {

    private Logger logger = LoggerFactory.getLogger(MyResponseOperationModelProvider.class);

    private final TypeResolver typeResolver;

    @Autowired
    private DynamicCreateModelClass dynamicCreateModelClass;

    @Autowired
    public MyResponseOperationModelProvider(TypeResolver typeResolver) {
        super(typeResolver);
        this.typeResolver = typeResolver;
    }



    @Override
    public void apply(RequestMappingContext context) {

        logger.debug("================  MyResponseOperationModelProvider is apply. dynamicCreateModelClass={}",dynamicCreateModelClass);

        Class clazz = dynamicCreateModelClass.getModelClass(context);

        logger.debug("================  MyResponseOperationModelProvider is apply. clazz={}",clazz);

        logger.debug("================  MyResponseOperationModelProvider getReturnType={} mappingPattern={} ",context.getReturnType(),context.getRequestMappingPattern());

//        if(clazz!=null) {
//            // 后面要用
//            RequestMappingContextUtil.put(context,clazz);
//
//            collectGlobalModels(context);
//            logger.debug("================  MyResponseOperationModelProvider is apply. collectGlobalModels is end!");
//        }




    }

    /**
     * from OperationModelsProvider
     * @param context
     */
    private void collectGlobalModels(RequestMappingContext context) {
        for (ResolvedType each : context.getAdditionalModels()) {
            context.operationModelsBuilder().addInputParam(each);
            context.operationModelsBuilder().addReturn(each);
        }
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }
}

package com.kq.swagger.customize.plugin;

import com.fasterxml.classmate.TypeResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationModelsProviderPlugin;
import springfox.documentation.spi.service.contexts.RequestMappingContext;
import springfox.documentation.spring.web.readers.operation.OperationModelsProvider;

/**
 * @see springfox.documentation.spring.web.readers.operation.OperationModelsProvider
 * @author kq
 * @date 2021-06-11 9:34
 * @since 2020-0630
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE+100)
public class MyResponseOperationModelProvider  extends OperationModelsProvider {// implements OperationModelsProviderPlugin {

    private Logger logger = LoggerFactory.getLogger(MyResponseOperationModelProvider.class);

    private final TypeResolver typeResolver;

    @Autowired
    public MyResponseOperationModelProvider(TypeResolver typeResolver) {
        super(typeResolver);
        this.typeResolver = typeResolver;
    }




    @Override
    public void apply(RequestMappingContext context) {

        logger.debug("================  MyResponseOperationModelProvider is apply.");

    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }
}

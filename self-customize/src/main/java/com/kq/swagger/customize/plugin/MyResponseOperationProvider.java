package com.kq.swagger.customize.plugin;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Header;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.RequestMappingContext;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author kq
 * @date 2021-06-11 14:02
 * @since 2020-0630
 */
@Deprecated
//@Component
//@Order
public class MyResponseOperationProvider implements OperationBuilderPlugin {

    private Logger logger = LoggerFactory.getLogger(MyResponseOperationProvider.class);

    @Autowired
    private TypeResolver typeResolver;

    @Override
    public void apply(OperationContext operationContext) {

        RequestMappingContext requestMappingContext = getRequestMappingContext(operationContext);

        logger.debug("RequestMappingContext context={}",requestMappingContext);

//        Class outerClass = RequestMappingContextUtil.get(requestMappingContext);
//
//        logger.debug("RequestMappingContext outerClass={}",outerClass);
//
//        if(outerClass!=null) {
//
//            ResolvedType outerResolvedType = typeResolver.resolve(outerClass);
//            operationContext.getDocumentationContext().getAdditionalModels().add(outerResolvedType);
//            ModelRef outerModelRef = new ModelRef(outerClass.getSimpleName());
//            Set<ResponseMessage> set = new LinkedHashSet<ResponseMessage>();
//            Map<String, Header> headers = new HashMap<String, Header>();
//            List<VendorExtension> vendorExtensions = new ArrayList<VendorExtension>();
//            set.add(new ResponseMessage(200,"返回json用例说明",outerModelRef,headers,vendorExtensions));
//            operationContext.operationBuilder().responseMessages(set);
//
//        }


    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }

    private RequestMappingContext getRequestMappingContext(OperationContext context) {

        try {
            Field field = OperationContext.class.getDeclaredField("requestContext");
            field.setAccessible(true);

            RequestMappingContext result =  (RequestMappingContext)field.get(context);

            return result;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

}

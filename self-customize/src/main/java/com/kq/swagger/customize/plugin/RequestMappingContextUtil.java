package com.kq.swagger.customize.plugin;

import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.RequestMappingContext;

import java.lang.reflect.Field;

/**
 * @author kq
 * @date 2021-06-11 13:54
 * @since 2020-0630
 */
public class RequestMappingContextUtil {

//    private static final Map<RequestMappingContext,Class> identityMap = new IdentityHashMap<>();
//
//    public static Class get(RequestMappingContext requestMappingContext) {
//
//        return identityMap.get(requestMappingContext);
//    }
//
//    public static Class put(RequestMappingContext requestMappingContext,Class clazz) {
//        return identityMap.put(requestMappingContext,clazz);
//    }
//
//    public static void remove(RequestMappingContext requestMappingContext){
//        identityMap.remove(requestMappingContext);
//    }

//    public static void main(String[] args) {
////        identityMap.put(new RequestMappingContext(null,null),null);
//
//        Map<String,Class> identityMap = new IdentityHashMap<>();
//        identityMap.put("a",null);
//
//    }


    public static RequestMappingContext getRequestMappingContext(OperationContext context) {

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

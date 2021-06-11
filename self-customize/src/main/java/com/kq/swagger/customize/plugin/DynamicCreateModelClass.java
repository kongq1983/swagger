package com.kq.swagger.customize.plugin;

import com.google.common.base.Optional;
import com.kq.swagger.customize.config.annotation.SwaggerResponseField;
import com.kq.swagger.customize.config.annotation.SwaggerResponseObject;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import springfox.documentation.spi.service.contexts.RequestMappingContext;

import java.util.*;

/**
 * @author kq
 * @date 2021-06-11 9:44
 * @since 2020-0630
 */

@Deprecated
//@Component
//@Order
public class DynamicCreateModelClass extends MyBaseBuildPlugin{

    public Class getModelClass(RequestMappingContext requestMappingContext) {
        // 只处理返回String 和 Map的

//        if(requestMappingContext.getReturnType().canCreateSubtype(String.class) ||
//                requestMappingContext.getReturnType().canCreateSubtype(Map.class)){
//            Optional<ApiReturnJsonArray> optionalArray = requestMappingContext.findAnnotation(ApiReturnJsonArray.class);
//            if(optionalArray.isPresent()){
//                for(ApiReturnJsonObject object : optionalArray.get().values()){
//                    this.createResultAnnotationRefModel(requestMappingContext,object,true);
//                }
//            }else{
        // 只要方法有SwaggerResponseObject就优先用这个
        Optional<SwaggerResponseObject> optional = requestMappingContext.findAnnotation(SwaggerResponseObject.class);
        if(optional.isPresent()){
            return this.createResultAnnotationRefModel(requestMappingContext,optional.get(),false);
        }

        return null;

    }

    /**
     * 创建Result的结果说明model类
     * @param requestMappingContext
     * @param object
     * @param isArray  resultData对应的类型是否为数组
     * 数据demo为：
     * {
     *   "resultCode": "200",
     *   "resultData": {
     *     "orderId": 6,
     *     "orderName": "订单一",
     *     "orderNum": 9
     *   },
     *   "resultMsg": "操作成功"
     * }
     * 或
     * {
     *   "resultCode": "200",
     *   "resultData": [{
     *     "orderId": 6,
     *     "orderName": "订单一",
     *     "orderNum": 9
     *   }],
     *   "resultMsg": "操作成功"
     * }
     * */
    private Class createResultAnnotationRefModel(RequestMappingContext requestMappingContext,SwaggerResponseObject object, boolean isArray){
        //model 名称
        String name = object.name();
//        int paramIndex = MyBaseBuildPlugin.paramIndex++;
        int paramIndex = this.atomicInt.getAndIncrement();
        SwaggerResponseField[] properties = object.value();
        //classname和resultname要尽量弄成不一样,不然汇报frozon class的错
        String classname = name + paramIndex;
        String resultname = "Result" + paramIndex;
        //创建Result的结果说明model类
        return this.createResultAnnotationRefModelObject(requestMappingContext,properties,resultname,classname,isArray);
    }


    /**
     * 创建Result的结果说明model类
     * Result类的结构,为两个map的嵌套
     * @param requestMappingContext
     * @param properties  里面那个map的列的注解数组
     * @param resultname  Result类的名称（注：因为是动态生成类，所以resultname的值不能相同）
     * @param classname   里面那个map的生成类名称（注：因为是动态生成类，所以classname的值不能相同）
     * @param isArray  resultData对应的类型是否为数组
     * */
    private Class createResultAnnotationRefModelObject(RequestMappingContext requestMappingContext,
                                                      SwaggerResponseField[] properties, String resultname, String classname, boolean isArray){
        ClassPool pool = ClassPool.getDefault();
        String classpath = basePackage + resultname;
        CtClass ctClass = pool.makeClass(classpath);

        try{
            //添加resultCode字段
            Map<String,Object> resultCodeMap = new HashMap<String,Object>(6);
            resultCodeMap.put("name","code");
            resultCodeMap.put("description","结果说明");
            resultCodeMap.put("example","200");
            resultCodeMap.put("value","结果编码");
            CtField resultCodeField = super.createField(resultCodeMap,ctClass);
            ctClass.addField(resultCodeField);

            //添加resultMsg字段
//            Map<String,Object> resultMsgMap = new HashMap<String,Object>(6);
//            resultMsgMap.put("name","msg");
//            resultMsgMap.put("description","结果说明");
//            resultMsgMap.put("example","操作成功");
//            resultMsgMap.put("value","结果说明");
//            CtField resultMsgField = super.createField(resultMsgMap,ctClass);
//            ctClass.addField(resultMsgField);

            //添加resultData字段
            //isSet == true,表示全部必选
            super.setRequiredAsDefaultValue(true);
            Class innerClass = createRefModel(requestMappingContext.getDocumentationContext(),properties,classname);
            Map<String,Object> resultDataMap = new HashMap<String,Object>(6);
            resultDataMap.put("name","result");
            resultDataMap.put("description","返回结果");
            resultDataMap.put("value","数据对象");
            String dataType = innerClass.getName();
            //转换成数组形式
            if(isArray){
                dataType = "[L" + innerClass.getName() + ";";
            }
            resultDataMap.put("dataType",dataType);
            CtField resultDataField = super.createField(resultDataMap,ctClass);
            ctClass.addField(resultDataField);

            Class outerClass = ctClass.toClass();

            return outerClass;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


}

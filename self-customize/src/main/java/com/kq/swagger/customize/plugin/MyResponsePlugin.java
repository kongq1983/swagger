package com.kq.swagger.customize.plugin;

import com.fasterxml.classmate.ResolvedType;
import com.google.common.base.Optional;
import com.kq.swagger.customize.config.annotation.SwaggerResponseField;
import com.kq.swagger.customize.config.annotation.SwaggerResponseObject;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
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
import springfox.documentation.spring.web.readers.operation.OperationModelsProvider;

import java.util.*;


/**
 * 此类用于给swagger添加json数据类型的返回结果的描述
 * MyOpertionPlugin
 *
 * @author kq
 * @date 2021/6/7 22:50
 * @since 1.0.0
 */
@Component
@Order
public class MyResponsePlugin extends MyBaseBuildPlugin implements OperationBuilderPlugin {

    @Autowired
    private OperationModelsProvider operationModelsProvider;


    @Override
    public void apply(OperationContext operationContext) {
        // 只处理返回String 和 Map的

//        if(operationContext.getReturnType().canCreateSubtype(String.class) ||
//                operationContext.getReturnType().canCreateSubtype(Map.class)){
//            Optional<ApiReturnJsonArray> optionalArray = operationContext.findAnnotation(ApiReturnJsonArray.class);
//            if(optionalArray.isPresent()){
//                for(ApiReturnJsonObject object : optionalArray.get().values()){
//                    this.createResultAnnotationRefModel(operationContext,object,true);
//                }
//            }else{
        // 只要方法有SwaggerResponseObject就优先用这个
                Optional<SwaggerResponseObject> optional = operationContext.findAnnotation(SwaggerResponseObject.class);
                if(optional.isPresent()){
                    this.createResultAnnotationRefModel(operationContext,optional.get(),false);
                }
//            }
//        }
    }

    /**
     * 创建Result的结果说明model类
     * @param operationContext
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
    private void createResultAnnotationRefModel(OperationContext operationContext, SwaggerResponseObject object, boolean isArray){
        //model 名称
        String name = object.name();
//        int paramIndex = MyBaseBuildPlugin.paramIndex++;
        int paramIndex = this.atomicInt.getAndIncrement();
        SwaggerResponseField[] properties = object.value();
        //classname和resultname要尽量弄成不一样,不然汇报frozon class的错
        String classname = name + paramIndex;
        String resultname = "Result" + paramIndex;
        //创建Result的结果说明model类
        this.createResultAnnotationRefModelObject(operationContext,properties,resultname,classname,isArray);
    }


    /**
     * 创建Result的结果说明model类
     * Result类的结构,为两个map的嵌套
     * @param operationContext
     * @param properties  里面那个map的列的注解数组
     * @param resultname  Result类的名称（注：因为是动态生成类，所以resultname的值不能相同）
     * @param classname   里面那个map的生成类名称（注：因为是动态生成类，所以classname的值不能相同）
     * @param isArray  resultData对应的类型是否为数组
     * */
    private void createResultAnnotationRefModelObject(OperationContext operationContext,
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
            Class innerClass = createRefModel(operationContext.getDocumentationContext(),properties,classname); // 这里会创建并添加到addional 比如ResponseObject0、1等 这个是ressult变量的对象
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
            ResolvedType outerResolvedType = typeResolver.resolve(outerClass);
            operationContext.getDocumentationContext().getAdditionalModels().add(outerResolvedType);

            // 触发一下，不触发 最后1个自定义的返回值有问题  (如果最后1个不是自定义返回值 没问题)
            RequestMappingContext requestMappingContext = RequestMappingContextUtil.getRequestMappingContext(operationContext);
            operationModelsProvider.apply(requestMappingContext);


            ModelRef outerModelRef = new ModelRef(outerClass.getSimpleName());
            Set<ResponseMessage> set = new LinkedHashSet<ResponseMessage>();
            Map<String, Header> headers = new HashMap<String, Header>();
            List<VendorExtension> vendorExtensions = new ArrayList<VendorExtension>();
            set.add(new ResponseMessage(200,"返回json用例说明",outerModelRef,headers,vendorExtensions));
            operationContext.operationBuilder().responseMessages(set);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }
}


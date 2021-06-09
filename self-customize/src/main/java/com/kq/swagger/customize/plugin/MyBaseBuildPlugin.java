package com.kq.swagger.customize.plugin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.kq.swagger.customize.config.annotation.SwaggerResponseField;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.service.contexts.DocumentationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**
 *
 * MyBaseBuildPlugin类用于动态构造一个类代表注解ApiReturnJsonArray和ApiReturnJsonObject的数据结果
 * 注：类名不能重复，不然会报frozon class的错
 *
 * MyBaseBuildPlugin
 *
 * @author kq
 * @date 2021/6/7 22:41
 * @since 1.0.0
 */
@Component
public class MyBaseBuildPlugin {

    @Autowired(required = false)
    protected TypeResolver typeResolver;

    /**
     * 参数的全局索引,用于动态生成类,确保类名不会重复
     * */
    public AtomicInteger atomicInt = new AtomicInteger();

    private ClassPool pool = ClassPool.getDefault();

    /**
     * 用于标记json结构中描述数据结构的key值
     * */
    private String childKey = "child";

    /**
     * 动态生成的Class名
     * */
    protected final static String basePackage = "com.kq.customize.javassist.dto.";


    protected final String StringTypeName = "java.lang.String";
    protected final String IntegerTypeName = "java.lang.Integer";
    protected final String LongTypeName = "java.lang.Long";
    protected final String FloatTypeName = "java.lang.Float";
    protected final String DoubleTypeName = "java.lang.Double";
    protected final String BooleanTypeName = "java.lang.Boolean";
    protected final String ShortTypeName = "java.lang.Short";

    /**
     * 根据propertys中的值动态生成含有Swagger注解的javaBeen
     * @param context
     * @param propertys
     * @param name 类名
     */
    protected Class createRefModel(DocumentationContext context, SwaggerResponseField[] propertys, String name) {
        CtClass ctClass = pool.makeClass(basePackage + name);
        try {
            this.translatePropertyToJSONObject(context,ctClass,propertys);

            Class clazz = ctClass.toClass();
            ResolvedType resolvedType = typeResolver.resolve(clazz);
            context.getAdditionalModels().add(resolvedType);
            return clazz;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * 此方法是实现了把当前方法上的所有ApiJsonProperty注解进行特定解析,解析成对应结构的json
     * 1、首先根据是否含有'.'来进行分组
     * 2、把含有'.'的部分重新进行解析,并把解析后的结果放到不含'.'的结果之中
     * 3、把解析完成的数据进行
     * @param context
     * @param ctClass
     * @param propertys
     * */
    protected void translatePropertyToJSONObject(DocumentationContext context, CtClass ctClass, SwaggerResponseField[] propertys) throws NotFoundException, CannotCompileException{
        List<SwaggerResponseField> propertyList = new ArrayList<>();
        JSONObject propertyMap = new JSONObject();
        for(SwaggerResponseField property : propertys){
            if(property.name().contains(".")){
                propertyList.add(property);
            }else{
                JSONObject propertyObj = this.transferAnnationToJSONObject(property);
                propertyMap.put(property.name(),propertyObj);
            }
        }
        for(SwaggerResponseField property : propertyList){
            JSONObject propertyObj = this.transferAnnationToJSONObject(property);
            String name = property.name();
            String[] names = name.split("\\.");
            propertyObj.put("name",names[1]);
            if(property.name().contains("[") && property.name().contains("]")){
                String paramName = names[0].substring(0,names[0].indexOf("["));
                int index = Integer.valueOf(names[0].substring(names[0].indexOf("[") + 1,names[0].indexOf("]")));
                if(propertyMap.containsKey(paramName)){
                    if(!propertyMap.getJSONObject(paramName).containsKey(this.childKey)){
                        propertyMap.getJSONObject(paramName).put(this.childKey,new JSONArray());
                    }
                    JSONArray array = propertyMap.getJSONObject(paramName).getJSONArray(this.childKey);
                    if(array.size() <= index || array.get(index) == null){
                        array.add(index,new JSONObject());
                    }
                    array.getJSONObject(index).put(names[1],propertyObj);
                }
            }else{
                if(propertyMap.containsKey(names[0])){
                    if(!propertyMap.getJSONObject(names[0]).containsKey(this.childKey)){
                        propertyMap.getJSONObject(names[0]).put(this.childKey,new JSONObject());
                    }
                    propertyMap.getJSONObject(names[0]).getJSONObject(this.childKey).put(names[1],propertyObj);
                }
            }
        }
        this.buildPropertyClass(context,ctClass,propertyMap);
    }

    /**
     *对JSONObject的数据进行类的动态生成,基本一层数据结构就要相应构造一个类
     * @param context
     * @param ctClass
     * @param propertyMap
     * */
    protected void buildPropertyClass(DocumentationContext context,CtClass ctClass,JSONObject propertyMap) throws NotFoundException, CannotCompileException{
        for (Object key : propertyMap.keySet()){
            JSONObject object = propertyMap.getJSONObject(ObjectUtils.toString(key));
            Object child = object.get(this.childKey);
            Map<String, Object> params = JSONObject.parseObject(object.toJSONString(),
                    new TypeReference<Map<String, Object>>(){});
            if(child == null || child instanceof String){
                CtField ctField = this.createField(params,ctClass);
                ctClass.addField(ctField);
            }else if(child instanceof JSONObject || child instanceof JSONArray) {
                String propertyClassName = ctClass.getName() + "." + key;
                CtClass propertyClass = pool.makeClass(propertyClassName);
                if(child instanceof JSONArray){
                    String datatype = "[L" + propertyClass.getName() + ";";
                    object.put("dataType",datatype);
                    params.put("dataType",datatype);
                    JSONObject innerObject = object.getJSONArray(this.childKey).getJSONObject(0);
                    this.buildPropertyClass(context,propertyClass,innerObject);
                }else{
                    object.put("dataType",propertyClass.getName());
                    params.put("dataType",propertyClass.getName());
                    JSONObject innerObject = object.getJSONObject(this.childKey);
                    this.buildPropertyClass(context,propertyClass,innerObject);
                }
                ResolvedType resolvedType = typeResolver.resolve(propertyClass.toClass());
                context.getAdditionalModels().add(resolvedType);

                CtField ctField = this.createField(params,ctClass);
                ctClass.addField(ctField);
            }
        }
    }

    /**
     * 根据property的值生成含有swagger apiModelProperty注解的属性
     * @param property
     * @param ctClass 字段所在的类
     */
    @SuppressWarnings("all")
    private CtField createField(SwaggerResponseField property, CtClass ctClass) throws NotFoundException, CannotCompileException {
        String dataType = this.getPackDataTypeByName(property.dataType());
        boolean required = property.required();
        //isSet == true,表示全部必选
        if(this.isSet){
            required = true;
        }

        CtClass fileType = pool.get(dataType);
        CtField ctField = new CtField(fileType, property.name(), ctClass);
        ctField.setModifiers(Modifier.PUBLIC);
        ConstPool constPool = ctClass.getClassFile().getConstPool();
        AnnotationsAttribute attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation ann = new Annotation("io.swagger.annotations.ApiModelProperty", constPool);
        //下面添加MemberValue的值对应注解ApiModelProperty的属性
        String value = property.value();
        //value为空时,展示description中的内容
        if(StringUtils.isBlank(value)){
            value = property.description();
        }
        ann.addMemberValue("value", new StringMemberValue(value, constPool));
        ann.addMemberValue("name", new StringMemberValue(property.name(), constPool));
        ann.addMemberValue("dataType", new StringMemberValue(dataType, constPool));
        ann.addMemberValue("example", new StringMemberValue(property.example(), constPool));
        ann.addMemberValue("required", new BooleanMemberValue(required, constPool));
        ann.addMemberValue("description", new StringMemberValue(property.description(), constPool));
        attr.addAnnotation(ann);
        ctField.getFieldInfo().addAttribute(attr);

        return ctField;
    }

    /**
     * 给一个字段加上ApiModelProperty的相关注解
     * @param map  ApiModelProperty相关注解要绑定的值
     * @param ctClass 字段所在的类
     * */
    @SuppressWarnings("all")
    protected CtField createField(Map<String,Object> map, CtClass ctClass) throws NotFoundException, CannotCompileException {
        String dataType = MapUtils.getString(map,"dataType","string");
        dataType = this.getPackDataTypeByName(dataType);
        String name = MapUtils.getString(map,"name","");
        String description = MapUtils.getString(map,"description","");
        String example = MapUtils.getString(map,"example","");
        String value = MapUtils.getString(map,"value","");
        boolean required = MapUtils.getBooleanValue(map,"required",true);
        if(this.isSet){
            required = true;
        }

        ConstPool constPool = ctClass.getClassFile().getConstPool();
        AnnotationsAttribute attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation ann = new Annotation("io.swagger.annotations.ApiModelProperty", constPool);
        //下面添加MemberValue的值对应注解ApiModelProperty的属性
        //value为空时,展示description中的内容
        if(StringUtils.isBlank(value)){
            value = description;
        }
        ann.addMemberValue("value", new StringMemberValue(value, constPool));
        ann.addMemberValue("name", new StringMemberValue(name, constPool));
        ann.addMemberValue("dataType", new StringMemberValue(dataType, constPool));
        ann.addMemberValue("example", new StringMemberValue(example, constPool));
        ann.addMemberValue("required", new BooleanMemberValue(required, constPool));
        ann.addMemberValue("description", new StringMemberValue(description, constPool));
        attr.addAnnotation(ann);

        CtClass fileType = pool.get(dataType);
        CtField ctField = new CtField(fileType,name, ctClass);
        ctField.setModifiers(Modifier.PUBLIC);
        ctField.getFieldInfo().addAttribute(attr);
        return ctField;
    }

    /**
     * 返回对应格式的MemberValue
     * */
    protected MemberValue getMemberValueByTypeName(String dataType,Object value,ConstPool constPool){
        //name参数中包含(.),直接判定为name就是包装的数据类型
        String typeName = this.getPackDataTypeByName(dataType);
        if(StringTypeName.equals(typeName)){
            return new StringMemberValue(ObjectUtils.toString(value),constPool);
        }else if(IntegerTypeName.equals(typeName)){
            Integer integer = Integer.valueOf(ObjectUtils.toString(value));
            return new IntegerMemberValue(integer,constPool);
        }else if(LongTypeName.equals(typeName)){
            Long val = Long.valueOf(ObjectUtils.toString(value));
            return new LongMemberValue(val,constPool);
        }else if(FloatTypeName.equals(typeName)){
            Float val = Float.valueOf(ObjectUtils.toString(value));
            return new FloatMemberValue(val,constPool);
        }else if(DoubleTypeName.equals(typeName)){
            Double val = Double.valueOf(ObjectUtils.toString(value));
            return new DoubleMemberValue(val,constPool);
        }else if(BooleanTypeName.equals(typeName)){
            Boolean val = Boolean.valueOf(ObjectUtils.toString(value));
            return new BooleanMemberValue(val,constPool);
        }else if(ShortTypeName.equals(typeName)){
            Short val = Short.valueOf(ObjectUtils.toString(value));
            return new ShortMemberValue(val,constPool);
        }
        return new ClassMemberValue(typeName,constPool);
    }


    /**
     * 根据基础数据类型的名称返回其对应的包装数据类型
     * @param name 基础数据类型名称(默认返回字符串类型)
     * */
    private String getPackDataTypeByName(String name){
        //name参数中包含(.),直接判定为name就是包装的数据类型
        if(name.indexOf(".") > -1){
            return name;
        }
        if("int".equals(name)){
            return IntegerTypeName;
        }else if("long".equals(name)){
            return LongTypeName;
        }else if("float".equals(name)){
            return FloatTypeName;
        }else if("double".equals(name)){
            return DoubleTypeName;
        }else if("boolean".equals(name)){
            return BooleanTypeName;
        }else if("short".equals(name)){
            return ShortTypeName;
        }
        return StringTypeName;
    }

    /***
     * 把ApiJsonProperty注解转换成JSONObject
     * @param property
     * */
    protected JSONObject transferAnnationToJSONObject(SwaggerResponseField property){
        JSONObject object = new JSONObject();
        object.put("name",property.name());
        object.put("value",property.value());
        object.put("example",property.example());
        object.put("description",property.description());
        object.put("dataType",property.dataType());
        object.put("required",property.required());
        //object.put("type",property.type());
        return object;
    }

    private boolean isSet = false;
    /**
     * 是否对ApiJsonProperty的required属性进行默认设值
     * @param
     * */
    protected void setRequiredAsDefaultValue(boolean isSet){
        this.isSet = isSet;
    }
}


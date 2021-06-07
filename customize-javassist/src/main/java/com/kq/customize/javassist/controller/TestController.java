package com.kq.customize.javassist.controller;

//import com.hua.demo.common.BaseController;
import com.kq.customize.javassist.dto.ApiJsonObject;
import com.kq.customize.javassist.dto.ApiJsonProperty;
import com.kq.customize.javassist.dto.ApiReturnJsonArray;
import com.kq.customize.javassist.dto.ApiReturnJsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;


/**
 * TestController
 *
 * @author kq
 * @date 2021/6/7 22:57
 * @since 1.0.0
 */
@Api(value = "TestController", tags = "测试接口", description = "测试接口")
@RestController
@RequestMapping("/testController")
public class TestController { // extends BaseController {

    @RequestMapping(value = "/testMethod")
    public String testMethod(){
        return "testMethod SUCCESS";
    }

    /**
     * 测试接口1：
     *   根据用户ID查询用户信息,一般只返回一条数据
     *   故使用ApiReturnJsonObject作为Result类resultData的类型
     * */
    @ApiOperation(value = "test1.do", notes = "测试接口1")
    @RequestMapping(value = "/test1.do", consumes = "application/json",method = RequestMethod.POST)
    @ApiReturnJsonObject(value = {
            @ApiJsonProperty(name = "userId", description = "用户ID", dataType = "long", example = "1"),
            @ApiJsonProperty(name = "userName", description = "用户姓名", example = "张三"),
            @ApiJsonProperty(name = "age", description = "年龄",dataType = "int", example = "18"),
            @ApiJsonProperty(name = "sex", description = "性别",example = "男")
    })
    public String test1(
            @ApiJsonObject(name = "paramMap", value = {
                    @ApiJsonProperty(name = "userId", description = "用户ID", dataType = "long", example = "1", required = true)
            })
            @RequestBody Map map){
        System.out.println("test1接口参数为" + map.toString());
        Map<String,Object> resultMap = new HashMap<String,Object>(2);
        resultMap.put("code",200);
        resultMap.put("msg","测试接口test1调用成功");
        return resultMap.toString();
    }

    /**
     * 测试接口2：
     *   根据用户姓名和性别模糊查询用户信息,一般会返回多条数据
     *   故使用ApiReturnJsonArray作为Result类resultData的类型
     * */
    @ApiOperation(value = "test2.do", notes = "测试接口2")
    @RequestMapping(value = "/test2.do", consumes = "application/json",method = RequestMethod.POST)
    @ApiReturnJsonArray(values = {
            @ApiReturnJsonObject(value = {
                    @ApiJsonProperty(name = "userId", description = "用户ID", dataType = "long", example = "1"),
                    @ApiJsonProperty(name = "userName", description = "用户姓名", example = "张三"),
                    @ApiJsonProperty(name = "age", description = "年龄",dataType = "int", example = "18"),
                    @ApiJsonProperty(name = "sex", description = "性别",example = "男")
            })
    })
    public String test2(
            @ApiJsonObject(name = "paramMap", value = {
                    @ApiJsonProperty(name = "userName", description = "用户姓名", example = "6", required = true),
                    @ApiJsonProperty(name = "sex", description = "性别", example = "5", required = true)
            })
            @RequestBody Map map){
        Map<String,Object> resultMap = new HashMap<String,Object>(2);
        resultMap.put("code",200);
        resultMap.put("msg","测试接口test2调用成功");
        System.out.println("test2接口参数为" + map.toString());
        return resultMap.toString();
    }
}


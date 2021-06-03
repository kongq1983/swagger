package com.kq.swaagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kq
 * @date 2021-06-03 17:10
 * @since 2020-0630
 */
@RestController
@RequestMapping("/api/demo")
@Api(tags = "入门控制器")
public class DemoController {

    @ApiOperation(value = "打招呼", notes = "测试方法")
    @ApiImplicitParam(name = "name", value = "姓名")
    @RequestMapping(value = "/sayhi", method = RequestMethod.POST)
    public String sayHi(String name) {
        return "Hello," + name;
    }

    @ApiOperation(value = "获取所有用户", notes = "查询分页数据")
    @RequestMapping(value = "/getall", method = RequestMethod.POST)
    public String getAll() {
        return "所有用户";
    }
}
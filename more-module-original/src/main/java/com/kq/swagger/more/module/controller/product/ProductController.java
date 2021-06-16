package com.kq.swagger.more.module.controller.product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kq
 * @date 2021-06-16 11:15
 * @since 2020-0630
 */
@RestController
@RequestMapping("/api/product")
@Api(tags = "产品控制器")
public class ProductController {


    @ApiOperation(value = "分页获取商品", notes = "查询分页数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list() {
        return "所有用户";
    }

}

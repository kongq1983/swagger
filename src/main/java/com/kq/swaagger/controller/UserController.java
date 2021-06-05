package com.kq.swaagger.controller;

import com.kq.swaagger.dto.DtoResult;
import com.kq.swaagger.entity.User;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author kq
 * @date 2021-06-03 17:10
 * @since 2020-0630
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户控制器")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private AtomicLong atomicLong = new AtomicLong();


    @ApiOperation(value = "用戶添加", notes = "用戶添加")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "username", value = "账号"),
        @ApiImplicitParam(name = "name", value = "姓名")
    })

//    @ApiResponses({
//            @ApiResponse()
//    })

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public DtoResult add(User user) {

        DtoResult result = new DtoResult();
        result.setCode("1000000");
        result.setResult(atomicLong.incrementAndGet());

        return result;

    }

    @ApiOperation(value = "获取所有用户", notes = "查询分页数据")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "curPage", value = "当前页" ,dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "curPage", value = "当前页" ,dataTypeClass = Integer.class,required = true,paramType = "path"),
            // 如果指定int基本类型   则要指定example 否则会报NumberFormatException
            @ApiImplicitParam(name = "pageSize", value = "每页记录数",dataType = "int",paramType = "query",example = "5"),
            @ApiImplicitParam(name = "status", value = "状态",dataType = "int" , paramType = "query",example = "1")
    })
    @RequestMapping(value = "/list/{curPage}", method = RequestMethod.POST)
    public String listPage(@PathVariable Integer curPage , @RequestParam(required = true)Integer pageSize, Integer status) {

        logger.debug("listPage curPage={} pageSize={} status={}",curPage,pageSize,status);

        return "分页用户";
    }
}
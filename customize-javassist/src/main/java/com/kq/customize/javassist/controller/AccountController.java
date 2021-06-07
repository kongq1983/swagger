package com.kq.customize.javassist.controller;

import com.kq.customize.javassist.dto.ApiJsonProperty;
import com.kq.customize.javassist.dto.ApiReturnJsonObject;
import com.kq.customize.javassist.dto.DtoGenericResult;
import com.kq.customize.javassist.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author kq
 * @date 2021-06-03 17:10
 * @since 2020-0630
 */
@RestController
@RequestMapping("/api/account")
@Api(tags = "账号控制器")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private AtomicLong atomicLong = new AtomicLong();


    @ApiOperation(value = "账号添加", notes = "账号添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号",dataType = "string" , paramType = "query"),
            @ApiImplicitParam(name = "name", value = "姓名",dataType = "string" , paramType = "query")
    })

//    @ApiResponses({
//            @ApiResponse()
//    })

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public DtoGenericResult<User> add(@ApiIgnore User user) {

        DtoGenericResult<User> result = new DtoGenericResult();
        result.setCode("1000000");

        User user1 = new User();
        user1.setId(atomicLong.incrementAndGet());

        result.setResult(user1);

        return result;

    }

    @ApiOperation(value = "账号修改 <?> ", notes = "账号修改 <?> ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用戶ID",dataType = "long",example = "0"),
            @ApiImplicitParam(name = "username", value = "账号"),
            @ApiImplicitParam(name = "name", value = "姓名")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public DtoGenericResult<?> update(User user) {

        DtoGenericResult<User> result = new DtoGenericResult();
        result.setCode("1000000");

        User user1 = new User();
        user1.setId(atomicLong.incrementAndGet());

        result.setResult(user1);

        return result;

    }

    @ApiOperation(value = "账号编辑<User>", notes = "账号编辑 <User>")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用戶ID",dataType = "long",example = "0"),
            @ApiImplicitParam(name = "username", value = "账号"),
            @ApiImplicitParam(name = "name", value = "姓名")
    })
    @ApiReturnJsonObject(value = {
            @ApiJsonProperty(name = "id", description = "用户ID", dataType = "long", example = "1"),
            @ApiJsonProperty(name = "username", description = "账号", example = "admin")

    })
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public DtoGenericResult<User> edit(User user) {

        DtoGenericResult<User> result = new DtoGenericResult();
        result.setCode("1000000");

        User user1 = new User();
        user1.setId(atomicLong.incrementAndGet());

        result.setResult(user1);

        return result;

    }

}

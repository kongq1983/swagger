package com.kq.swagger.customize.controller;

import com.kq.swagger.customize.config.annotation.SwaggerResponseField;
import com.kq.swagger.customize.config.annotation.SwaggerResponseObject;
import com.kq.swagger.customize.config.dto.DtoGenericResult;
import com.kq.swagger.customize.entity.User;
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
public class AccountController1 {

    private static final Logger logger = LoggerFactory.getLogger(AccountController1.class);

    private AtomicLong atomicLong = new AtomicLong();




    @ApiOperation(value = "账号修改 <?> ", notes = "账号修改 <?> ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用戶ID",dataType = "long",example = "0"),
            @ApiImplicitParam(name = "username", value = "账号"),
            @ApiImplicitParam(name = "name", value = "姓名")
    })
    @SwaggerResponseObject(value = {
            @SwaggerResponseField(name = "id", description = "用户ID", dataType = "long", example = "1"),
            @SwaggerResponseField(name = "username", description = "账号", example = "admin")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    // 注意SwaggerResponseObject和<?> 不能搭配
    public DtoGenericResult update(User user) {

        DtoGenericResult<User> result = new DtoGenericResult();
        result.setCode("1000000");

        User user1 = new User();
        user1.setId(atomicLong.incrementAndGet());

        result.setResult(user1);

        return result;

    }

    @ApiOperation(value = "账号编辑-自定义和泛型<User>", notes = "账号编辑-自定义和泛型 <User>")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用戶ID",dataType = "long",example = "0"),
            @ApiImplicitParam(name = "username", value = "账号"),
            @ApiImplicitParam(name = "name", value = "姓名")
    })
    @SwaggerResponseObject(value = {
            @SwaggerResponseField(name = "id", description = "用户ID", dataType = "long", example = "1"),
            @SwaggerResponseField(name = "username", description = "账号", example = "admin")
    })
    @RequestMapping(value = "/edit1", method = RequestMethod.POST)
    public DtoGenericResult<User> edit1(User user) {

        DtoGenericResult<User> result = new DtoGenericResult();
        result.setCode("1000000");

        User user1 = new User();
        user1.setId(atomicLong.incrementAndGet());

        result.setResult(user1);

        return result;

    }


//    @ApiOperation(value = "账号编辑-自定义和泛型2<User>", notes = "账号编辑-自定义和泛型2 <User>")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "用戶ID",dataType = "long",example = "0"),
//            @ApiImplicitParam(name = "username", value = "账号"),
//            @ApiImplicitParam(name = "name", value = "姓名")
//    })
//    @SwaggerResponseObject(value = {
//            @SwaggerResponseField(name = "id", description = "用户ID", dataType = "long", example = "1"),
//            @SwaggerResponseField(name = "username", description = "账号", example = "admin")
//    })
//    @RequestMapping(value = "/edit2", method = RequestMethod.POST)
//    public DtoGenericResult<User> edit2(User user) {
//
//        DtoGenericResult<User> result = new DtoGenericResult();
//        result.setCode("1000000");
//
//        User user1 = new User();
//        user1.setId(atomicLong.incrementAndGet());
//
//        result.setResult(user1);
//
//        return result;
//
//    }


}

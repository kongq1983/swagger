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




    @ApiOperation(value = "账号添加 ", notes = "账号添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用戶ID",dataType = "long",example = "0"),
            @ApiImplicitParam(name = "username", value = "账号"),
            @ApiImplicitParam(name = "name", value = "姓名"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token标记", required = true)

    })
    @SwaggerResponseObject(value = {
            @SwaggerResponseField(name = "id", description = "用户ID", dataType = "long", example = "1")
    })
//    @Produces({"application/json", "application/xml"})
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    // 注意SwaggerResponseObject和<?> 不能搭配
    public DtoGenericResult add(User user) {

        DtoGenericResult<User> result = new DtoGenericResult();
        result.setCode("1000000");

        User user1 = new User();
        user1.setId(atomicLong.incrementAndGet());

        result.setResult(user1);

        return result;

    }

    @ApiOperation(value = "账号修改", notes = "账号修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用戶ID",dataType = "long",example = "0"),
            @ApiImplicitParam(name = "username", value = "账号"),
            @ApiImplicitParam(name = "name", value = "姓名")
    })
    @SwaggerResponseObject(value = {
            @SwaggerResponseField(name = "id", description = "用户ID", dataType = "long", example = "1"),
            @SwaggerResponseField(name = "username", description = "账号", example = "admin"),
            @SwaggerResponseField(name = "teacher", description = "老师", example = "teacher",type = User.class)
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

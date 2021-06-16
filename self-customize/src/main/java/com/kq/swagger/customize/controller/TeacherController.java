package com.kq.swagger.customize.controller;

import com.kq.swagger.customize.config.annotation.SwaggerResponseField;
import com.kq.swagger.customize.config.annotation.SwaggerResponseObject;
import com.kq.swagger.customize.config.dto.DtoGenericResult;
import com.kq.swagger.customize.entity.Teacher;
import com.kq.swagger.customize.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kq
 * @date 2021-06-10 15:06
 * @since 2020-0630
 */

@RestController
@RequestMapping("/api/teacher")
@Api(tags = "Teacher控制器")
public class TeacherController {


    @ApiOperation(value = "账号列表", notes = "账号列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "username", value = "账号"),
        @ApiImplicitParam(name = "name", value = "姓名")
    })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public DtoGenericResult<Teacher> list(User user) {

        DtoGenericResult<Teacher> result = new DtoGenericResult();
        result.setCode("1000000");

//        User user1 = new User();
//        user1.setId(atomicLong.incrementAndGet());
//        result.setResult(user1);

        return result;

    }


    @ApiOperation(value = "老师修改", notes = "老师修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用戶ID",dataType = "long",example = "0"),
            @ApiImplicitParam(name = "username", value = "账号"),
            @ApiImplicitParam(name = "name", value = "姓名")
    })
    @SwaggerResponseObject(value = {
            @SwaggerResponseField(name = "id", description = "用户ID", dataType = "long", example = "1"),
            @SwaggerResponseField(name = "username", description = "账号", example = "admin"),
            @SwaggerResponseField(name = "teacher", description = "老师", example = "teacher"),
            @SwaggerResponseField(name = "school", description = "学校", example = "obj", dataType="school"),
            @SwaggerResponseField(name = "school.id", description = "学校ID", example = "0",dataType="int"),
            @SwaggerResponseField(name = "school.code", description = "学校编号", example = "0001"),
            @SwaggerResponseField(name = "school.master", description = "校长", example = "obj", dataType="child"),
            @SwaggerResponseField(name = "school.master.id", description = "校长ID", example = "obj", dataType="int"),
            @SwaggerResponseField(name = "school.master.name", description = "校长姓名", example = "obj"),
            @SwaggerResponseField(name = "address", description = "地址", example = "obj", dataType="home"),
            @SwaggerResponseField(name = "address.id", description = "地址ID", example = "0",dataType="int"),
            @SwaggerResponseField(name = "address.code", description = "地址邮编", example = "0002"),
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public DtoGenericResult<User> add(User user) {

        DtoGenericResult<User> result = new DtoGenericResult();
        result.setCode("1000000");

        User user1 = new User();

        result.setResult(user1);

        return result;

    }


    @ApiOperation(value = "老师修改", notes = "老师修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用戶ID",dataType = "long",example = "0"),
            @ApiImplicitParam(name = "username", value = "账号"),
            @ApiImplicitParam(name = "name", value = "姓名")
    })
    @SwaggerResponseObject(value = {
            @SwaggerResponseField(name = "id", description = "用户ID", dataType = "long", example = "1"),
            @SwaggerResponseField(name = "username", description = "账号", example = "admin"),
            @SwaggerResponseField(name = "teacher", description = "老师", example = "teacher"),
            @SwaggerResponseField(name = "school", description = "学校", example = "obj", dataType="school"),
            @SwaggerResponseField(name = "school.id", description = "学校ID", example = "0",dataType="int"),
            @SwaggerResponseField(name = "school.code", description = "学校编号", example = "0001"),
            @SwaggerResponseField(name = "school.name", description = "学校名称", example = "high school"),
            @SwaggerResponseField(name = "school.address", description = "学校地址", example = "hz.zj"
            )
    })
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public DtoGenericResult<User> edit(User user) {

        DtoGenericResult<User> result = new DtoGenericResult();
        result.setCode("1000000");

        User user1 = new User();

        result.setResult(user1);

        return result;

    }


}

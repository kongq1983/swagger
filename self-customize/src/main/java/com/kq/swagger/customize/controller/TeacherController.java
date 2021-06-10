package com.kq.swagger.customize.controller;

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


}

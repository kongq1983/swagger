package com.kq.swagger.customize.controller;

import com.kq.swagger.customize.config.dto.DtoGenericResult;
import com.kq.swagger.customize.entity.School;
import com.kq.swagger.customize.entity.Teacher;
import com.kq.swagger.customize.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author kq
 * @date 2021-06-15 10:14
 * @since 2020-0630
 */

@RestController
@RequestMapping("/api/school")
@Api(tags = "学校控制器")
public class SchoolController {


    @ApiOperation(value = "老师列表", notes = "老师列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号",dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "姓名",dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public DtoGenericResult<School> list(@ApiIgnore  User user) {

        DtoGenericResult<School> result = new DtoGenericResult();
        result.setCode("1000000");

//        User user1 = new User();
//        user1.setId(atomicLong.incrementAndGet());
//        result.setResult(user1);

        return result;

    }

}

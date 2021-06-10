package com.kq.swagger.customize.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kq
 * @date 2021-06-10 15:04
 * @since 2020-0630
 */

@RestController
@RequestMapping("/api/user")
@Api(tags = "用户控制器")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);



}

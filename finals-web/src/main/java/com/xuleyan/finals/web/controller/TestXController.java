/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.controller;

import com.xuleyan.finals.web.Service.UserAService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 * @author xuleyan
 * @version TestController.java, v 0.1 2021-07-12 9:45 上午
 */
@RestController
public class TestXController {

    @Resource
    private UserAService userAService;

    @GetMapping("test")
    public String test() {
        return userAService.test();
    }

}
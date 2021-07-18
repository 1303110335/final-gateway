/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.Service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 * @author xuleyan
 * @version UserAService.java, v 0.1 2021-07-12 9:31 上午
 */
@Component
//@Scope("prototype")
public class UserAService {

    @Resource
    private CommonService commonService;

    public String test() {
        commonService.test();
        return "success";
    }
}
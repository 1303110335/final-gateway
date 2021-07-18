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
 * @version CommonService.java, v 0.1 2021-07-12 9:32 上午
 */
@Component
//@Scope("prototype")
public class CommonService {

    @Resource
    private UserAService userAService;

    public void test() {
        System.out.println("test");
    }
}
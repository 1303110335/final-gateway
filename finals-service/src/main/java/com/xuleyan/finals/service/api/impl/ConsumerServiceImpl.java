/**
 * fshows.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.xuleyan.finals.service.api.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xuleyan.finals.service.api.ConsumerService;
import com.xuleyan.finals.service.api.param.ConsumerParam;
import com.xuleyan.provider.facade.UserDubboFacade;
import org.springframework.stereotype.Service;

/**
 * @author xuleyan
 * @version ConsumerService.java, v 0.1 2020-05-29 5:47 PM xuleyan
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Reference
    private UserDubboFacade userFacade;

    @Override
    public String hello(ConsumerParam param) {
        String someHandler = userFacade.dubboHandler();
        return "hello spring boot world " + param.getName() + someHandler;
    }
}
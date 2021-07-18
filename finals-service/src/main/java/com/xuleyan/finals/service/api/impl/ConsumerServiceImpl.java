///**
// * fshows.com
// * Copyright (C) 2013-2020 All Rights Reserved.
// */
//package com.xuleyan.finals.service.api.impl;
//
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.alibaba.fastjson.JSON;
//import com.alijk.bq.result.ResponseWrapper;
//import com.alijk.bqhealth.cloud.dto.HealthCloudPopulationDTO;
//import com.alijk.bqhealth.cloud.facade.HealthCloudPopulationFacade;
//import com.xuleyan.finals.service.api.ConsumerService;
//import com.xuleyan.finals.service.api.param.ConsumerParam;
//import com.xuleyan.provider.facade.UserDubboFacade;
//import org.springframework.stereotype.Service;
//
///**
// * @author xuleyan
// * @version ConsumerService.java, v 0.1 2020-05-29 5:47 PM xuleyan
// */
//@Service
//public class ConsumerServiceImpl implements ConsumerService {
//
//    @Reference
//    private UserDubboFacade userFacade;
//
//    private HealthCloudPopulationFacade healthCloudPopulationFacade;
//
//    @Override
//    public String hello(ConsumerParam param) {
//        String someHandler = userFacade.dubboHandler();
//        ResponseWrapper<HealthCloudPopulationDTO> responseWrapper = healthCloudPopulationFacade.populationStatistic();
//        System.out.println(JSON.toJSONString(responseWrapper.getData()));
//        return "hello spring boot world " + param.getName() + someHandler;
//
//    }
//}
///**
// * fshows.com
// * Copyright (C) 2013-2020 All Rights Reserved.
// */
//package com.xuleyan.finals.web.controller;
//
//import com.xuleyan.finals.web.Service.NewApiInvokeService;
//import com.xuleyan.frame.core.util.LogUtil;
//import com.xuleyan.frame.web.BaseController;
//import com.xuleyan.frame.web.domain.ApiResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.util.WebUtils;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Map;
//
///**
// * @author xuleyan
// * @version NewGatewayController.java, v 0.1 2020-06-01 11:27 AM xuleyan
// */
//@RestController
//@Slf4j
//public class NewGatewayController extends BaseController {
//
//    @Autowired
//    private NewApiInvokeService newApiInvokeService;
//
//    @PostMapping("/newGateway")
//    public ApiResponse gateway(@RequestParam(value = "appid") String appId,
//                               @RequestParam(value = "method") String method,
//                               @RequestParam(value = "sign") String sign,
//                               @RequestParam(value = "version") String version,
//                               @RequestParam(value = "content") String content,
//                               HttpServletRequest request) throws Throwable {
//        Map<String, Object> parameters = WebUtils.getParametersStartingWith(request, "");
//        LogUtil.info(log, "【API请求参数】 param={}", parameters);
//        Object result = newApiInvokeService.invoke(appId, sign, method, parameters, content);
//        LogUtil.info(log, "【API请求响应】 param={},result={}", parameters, result);
//        return success(result);
//    }
//}
///**
// * fshows.com
// * Copyright (C) 2013-2019 All Rights Reserved.
// */
//package com.xuleyan.finals.web.Service;
//
//import com.xuleyan.frame.web.service.AbstractApiInvokeService;
//import jodd.util.StringUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author xuleyan
// * @version NewApiInvokeService.java, v 0.1 2019-12-26 8:41 PM xuleyan
// */
//@Service
//@Slf4j
//public class NewApiInvokeService extends AbstractApiInvokeService {
//
//    private final List<String> methodNameList = Arrays.asList("com.fshows.lifecircle.user.login",
//            "com.fshows.lifecircle.user.query.info",
//            "com.fshows.lifecircle.user.send.sms.code",
//            "com.fshows.lifecircle.user.check.sms.code",
//            "com.fshows.lifecircle.user.set.password",
//            "com.fshows.lifecircle.protocol.list",
//            "com.fshows.lifecircle.protocol.detail",
//            "com.fshows.lifecircle.new.app.scan.code",
//            "com.fshows.lifecircle.user.login.info");
//    @Autowired
//    private HttpServletRequest httpServletRequest;
//
//    @Override
//    protected Boolean checkPermission(String appId, String apiMethodName) {
//
//        return true;
//    }
//
//    @Override
//    protected Boolean checkSign(Map<String, Object> params, String appId) {
//
//        return true;
//    }
//
//    /**
//     * 拼接acessToken到content
//     *
//     * @param content
//     * @return
//     */
//    public String concatContentAndLoginInfo(String content) {
//        String accessToken = httpServletRequest.getHeader("accessToken");
//        if (StringUtils.isNotBlank(content) && StringUtils.isNotBlank(accessToken)) {
//
//            content = StringUtil.replaceLast(content, "}", "");
//            if (!"{".equals(content)) {
//                content += ",";
//            }
//            content += "\"accessToken\":\"" + accessToken + "\"}";
//        }
//        return content;
//    }
//}
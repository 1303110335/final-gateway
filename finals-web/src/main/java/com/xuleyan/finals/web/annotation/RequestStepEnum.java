/**
 * xuleyan.com
 * Copyright (C) 2013-2021All Rights Reserved.
 */
package com.xuleyan.finals.web.annotation;

/**
 *
 * @author xuleyan
 * @version RequestStepEnum.java, v 0.1 2021-07-19 5:53 下午
 */
public enum RequestStepEnum {

    /**
     * 请求体加密且有用户数据
     * 返回体带有私钥并带有签名及ticket
     * login登录请求
     */
    login,

    /**
     * 获取token阶段
     * 请求体带有ticket, 且加密
     */
    token,

    /**
     * 默认是service阶段，签名，加密等
     */
    service,

    /**
     * 仅加解密, 不校验 token 等参数
     */
    ONLY_SECURITY,

    ;
}
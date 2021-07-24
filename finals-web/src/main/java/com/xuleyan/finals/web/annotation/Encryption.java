/**
 * xuleyan.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.annotation;

import java.lang.annotation.*;

/**
 *
 * @author xuleyan
 * @version Encryption.java, v 0.1 2021-07-19 5:50 下午
 */
@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Encryption {

    String decryptionKey() default "";

    /**
     * 请求步骤
     */
    RequestStepEnum requestStep() default RequestStepEnum.service;
}
/**
 * bianque.com
 * Copyright (C) 2013-2021All Rights Reserved.
 */
package com.xuleyan.finals.web.annotation;

import java.lang.annotation.*;

/**
 * 对该方法或类返回的内容进行脱敏
 * @author xuleyan
 * @version Masking.java, v 0.1 2021-07-20 6:21 上午
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Masking {
}
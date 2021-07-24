/**
 * xuleyan.com
 * Copyright (C) 2013-2021All Rights Reserved.
 */
package com.xuleyan.finals.web.annotation;

import java.lang.annotation.*;

/**
 *
 * @author xuleyan
 * @version GoodsLimiter.java, v 0.1 2021-07-22 11:17 上午
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GoodsLimiter {

    /**
     * 限流大小，每秒数量
     * @return
     */
    int limit();
}
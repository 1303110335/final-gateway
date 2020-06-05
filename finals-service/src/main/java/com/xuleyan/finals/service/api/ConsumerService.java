/**
 * fshows.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.xuleyan.finals.service.api;

import com.xuleyan.finals.service.api.param.ConsumerParam;
import com.xuleyan.frame.core.annotation.XuApi;

/**
 * @author xuleyan
 * @version ConsumerService.java, v 0.1 2020-06-01 11:51 AM xuleyan
 */
public interface ConsumerService {

    @XuApi(name = "com.xuleyan.consumer.hello")
    public String hello(ConsumerParam param);
}

/**
 * xuleyan.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.config;

import com.xuleyan.finals.web.transaction.GoodsProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 *
 * @author xuleyan
 * @version RocketMqConfig.java, v 0.1 2021-07-21 5:57 下午
 */
@Component
public class RocketMqConfig {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @PostConstruct
    public void init() {
        GoodsProducer.mqTemplate = rocketMQTemplate;
    }

}
///**
// * xuleyan.com
// * Copyright (C) 2013-2021 All Rights Reserved.
// */
//package com.xuleyan.finals.web.listener;
//
//import com.xuleyan.finals.common.constants.TopicConstants;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.stereotype.Component;
//
///**
// *
// * @author xuleyan
// * @version GoodsRocketMqListener.java, v 0.1 2021-07-22 3:55 下午
// */
//@Slf4j
//@Component
//@RocketMQMessageListener(topic = TopicConstants.GOODS_TOPIC, consumerGroup = TopicConstants.GOODS_CONSUMER_GROUP)
//public class GoodsRocketMqListener implements RocketMQListener<MessageExt> {
//
//    @Override
//    public void onMessage(MessageExt message) {
//        byte[] body = message.getBody();
//        String msg = new String(body);
//        log.info("接收到消息: {}, 重试次数: {}", msg, message.getReconsumeTimes());
//    }
//}
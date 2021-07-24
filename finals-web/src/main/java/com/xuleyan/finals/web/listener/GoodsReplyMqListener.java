///**
// * bianque.com
// * Copyright (C) 2013-2021 All Rights Reserved.
// */
//package com.xuleyan.finals.web.listener;
//
//import com.xuleyan.finals.common.constants.TopicConstants;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQReplyListener;
//import org.springframework.stereotype.Component;
//
///**
// *
// * @author xuleyan
// * @version GoodsReplyMqListener.java, v 0.1 2021-07-22 4:35 下午
// */
//@Slf4j
//@Component
//@RocketMQMessageListener(topic = TopicConstants.GOODS_TOPIC, consumerGroup = TopicConstants.GOODS_CONSUMER_GROUP)
//public class GoodsReplyMqListener implements RocketMQReplyListener<MessageExt, String> {
//
//    @Override
//    public String onMessage(MessageExt message) {
//        log.info("接收到消息: {}, 重试次数: {}", message, message.getReconsumeTimes());
//        return "success";
//    }
//}
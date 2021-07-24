/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.listener;

import com.alibaba.fastjson.JSON;
import com.xuleyan.finals.common.constants.TopicConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 * @author xuleyan
 * @version GoodsConsumer.java, v 0.1 2021-07-22 8:28 下午
 */
@Component
@Slf4j
public class GoodsConsumer {

    @Value("${rocketmq.name-server}")
    private String nameServer;

    @PostConstruct
    public void init() {
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(TopicConstants.GOODS_CONSUMER_GROUP_2);
            consumer.subscribe(TopicConstants.GOODS_TOPIC, "*");
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.setNamesrvAddr(nameServer);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    log.info("Receive new Message:{}", JSON.toJSONString(msgs));
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
            log.info("consumer started..");
        } catch (MQClientException e) {
            e.printStackTrace();
        }

    }
}
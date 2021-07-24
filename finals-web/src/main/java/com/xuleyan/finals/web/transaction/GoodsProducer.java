/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.transaction;

import com.xuleyan.finals.service.api.param.GoodsParam;
import com.xuleyan.finals.web.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author xuleyan
 * @version GoodsProducer.java, v 0.1 2021-07-21 5:59 下午
 */
@Slf4j
public class GoodsProducer {

    public static RocketMQTemplate mqTemplate;

    private static GoodsTransactionListener transactionListener;

    public static void setTransactionListener(GoodsTransactionListener transactionListener) {
        GoodsProducer.transactionListener = transactionListener;
    }

    /**
     * 同步发送
     * @param topic
     * @param body
     * @return
     */
    public static boolean send(String topic, String body) {
        SendResult sendResult = mqTemplate.syncSend(topic, MessageBuilder.withPayload(body).build());
        if (sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
            return true;
        }
        log.error("发送数据失败, topic={}, body={}", topic, body);
        return false;
    }

    /**
     * 发送异步消息
     * @param topic
     * @param body
     * @param sendCallback
     */
    public static void sendAsync(String topic, String body, SendCallback sendCallback) {
        mqTemplate.asyncSend(topic, MessageBuilder.withPayload(body).build(), sendCallback);
    }

    /**
     * 发送延时消息
     * 延时消息等级分为18个：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     * @param topic      主题
     * @param body       消息
     * @param delayLevel 延迟等级
     * @author tyg
     * @date 2021-03-24 14:55
     * @return void
     */
    public static boolean sendDelay(String topic, String body, Integer delayLevel){
        SendResult result = mqTemplate.syncSend(topic, MessageBuilder.withPayload(body).build(), delayLevel);
        if (result.getSendStatus() != SendStatus.SEND_OK){
            log.error("\n=======消息发送失败，topic：{}，延迟等级：{}，数据：{}", topic, delayLevel, body);
            return false;
        }
        return true;
    }

    /**
     * 发送带tag的消息，格式：topic:tag，示例：order_topic:myTag
     * @param topic 主题
     * @param tag   tag
     * @param body  消息
     * @author tyg
     * @date 2021-03-24 14:55
     * @return void
     */
    public static boolean sendTag(String topic, String tag, String body){
        SendResult result = mqTemplate.syncSend(String.format("%s:%s", topic, tag), MessageBuilder.withPayload(body).build());
        if (result.getSendStatus() != SendStatus.SEND_OK){
            log.error("\n=======消息发送失败，topic：{}，tag:{}，数据：{}", topic, tag, body);
            return false;
        }
        return true;
    }

    /**
     * 单向(Oneway)发送，不可靠，可能存在丢数据的风险，建议在一些日志收集时使用
     * 由于在 oneway 方式发送消息时没有请求应答处理，一旦出现消息发送失败，则会因为没有重试而导致数据丢失。若数据不可丢，建议选用可靠同步或可靠异步发送方式。
     * @param topic 主题
     * @param body  消息
     * @author tyg
     * @date 2021-03-24 14:55
     * @return void
     */
    public static void sendOneWay(String topic, String body){
        mqTemplate.sendOneWay(topic, MessageBuilder.withPayload(body).build());
    }

    /**
     * 发送事务消息
     * @param topic 主题
     * @param body 消息
     * @param object 额外信息
     */
    public static boolean sendTransMessage(String topic, String body, Object object) {
        ((TransactionMQProducer)mqTemplate.getProducer()).setTransactionListener(transactionListener);
        //Message<String> message = MessageBuilder.withPayload(body).build();

        Map<String, Object> headers = new HashMap<>(3);
        headers.put("requestId", ((GoodsParam)object).getRequestId());
        headers.put("timestamp", DateTimeUtil.format(new Date(), DateTimeUtil.LL_DATETIME_PATTERN));
        MessageHeaders messageHeaders = new MessageHeaders(headers);
        Message<String> message = MessageBuilder.createMessage(body, messageHeaders);
        TransactionSendResult transactionSendResult = mqTemplate.sendMessageInTransaction(topic, message, object);
        if (transactionSendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
            if (transactionSendResult.getLocalTransactionState().equals(LocalTransactionState.COMMIT_MESSAGE)) {
                return true;
            } else {
                log.error("\n=======消息发送成功，但是事务失败，topic：{}，body:{}，object：{}, transactionSendResult: {}",
                        topic, body, object, transactionSendResult);
                return false;
            }
        }
        log.error("\n=======消息发送失败，topic：{}，body:{}，object：{}, transactionSendResult: {}", topic, body, object, transactionSendResult);
        return false;
    }

}
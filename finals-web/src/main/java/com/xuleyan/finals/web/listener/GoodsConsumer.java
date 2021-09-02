/**
 * xuleyan.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.listener;

import com.alibaba.fastjson.JSON;
import com.xuleyan.finals.common.constants.AccountConstants;
import com.xuleyan.finals.common.constants.TopicConstants;
import com.xuleyan.finals.dal.pojo.Account;
import com.xuleyan.finals.dal.pojo.GoodsSecondsKill;
import com.xuleyan.finals.service.api.AccountService;
import com.xuleyan.finals.service.api.param.GoodsParam;
import com.xuleyan.frame.extend.redis.jedis.JedisTemplate;
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
import javax.annotation.Resource;
import java.util.Arrays;
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

    @Resource
    private AccountService accountService;

    @Resource
    private JedisTemplate jedisTemplate;

    @PostConstruct
    public void init() {
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(TopicConstants.GOODS_CONSUMER_GROUP);
            consumer.subscribe(TopicConstants.GOODS_TOPIC, "*");
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.setNamesrvAddr(nameServer);
            consumer.setConsumeMessageBatchMaxSize(1);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    log.info("接收到消息:{}", JSON.toJSONString(msgs));
                    for (MessageExt msg : msgs) {
                        String requestId = new String(msg.getBody());
                        GoodsParam goodsParam = new GoodsParam();
                        Integer id = 1;
                        goodsParam.setId(id);
                        goodsParam.setRequestId(requestId);
                        String key = AccountConstants.GOODS_KEY;
                        try {
                            String goodsNumStr = jedisTemplate.get(key);
                            Integer goodsNum = 0;
                            if (goodsNumStr == null) {
                                Account account = accountService.findOne(id);
                                goodsNum = account.getGoods();
                                jedisTemplate.setEx(key, goodsNum.toString(), AccountConstants.GOODS_TIME);
                            } else {
                                goodsNum = Integer.parseInt(goodsNumStr);
                            }
                            if (goodsNum > 0) {
                                log.info("insertAndSubGoods >> 进行商品消费 >> requestId = {}, goods = {}", requestId, goodsNum);
                                accountService.insertAndSubGoods(goodsParam);
                                jedisTemplate.increBy(key, -1L);
                            } else {
                                log.info("商品已经抢购一空, requestId = {}", requestId);
                            }
                        } catch (Exception e) {
                            log.info("消费商品失败【前方排队拥挤，请重试】");
                        }
                    }

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
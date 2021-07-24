/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.transaction;

import com.xuleyan.finals.common.constants.AccountConstants;
import com.xuleyan.finals.dal.pojo.GoodsSecondsKill;
import com.xuleyan.finals.service.api.AccountService;
import com.xuleyan.finals.service.api.param.GoodsParam;
import com.xuleyan.frame.extend.redis.jedis.JedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 事务消息listener
 * @author xuleyan
 * @version GoodsTransactionListener.java, v 0.1 2021-07-21 5:39 下午
 */
@Component
@Slf4j
public class GoodsTransactionListener implements TransactionListener {

    @Resource
    private AccountService accountService;

    /**
     * 执行本地事务消息
     * @param message
     * @param object
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object object) {
        // transactionId = requestId
        String requestId = message.getUserProperty("requestId");
        // 本地插入提现记录，扣减用户余额
        GoodsParam goodsParam = (GoodsParam) object;
        try {
            accountService.insertAndSubGoods(goodsParam);
            return LocalTransactionState.COMMIT_MESSAGE;
        } catch (Exception e) {
            log.error("执行本地事务失败", e);
        }
        return LocalTransactionState.ROLLBACK_MESSAGE;
    }

    /**
     * 判断事务消息是否成功
     * @param messageExt
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        String transactionId = messageExt.getUserProperty("requestId");
        if (transactionId == null) {
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        GoodsSecondsKill goods = accountService.findGoods(transactionId);
        if (goods != null) {
            return LocalTransactionState.COMMIT_MESSAGE;
        }
        return LocalTransactionState.ROLLBACK_MESSAGE;
    }
}
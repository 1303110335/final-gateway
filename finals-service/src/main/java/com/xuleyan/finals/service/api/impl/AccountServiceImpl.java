/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.service.api.impl;

import com.xuleyan.finals.common.constants.AccountConstants;
import com.xuleyan.finals.dal.mapper.AccountExtMapper;
import com.xuleyan.finals.dal.mapper.AccountMapper;
import com.xuleyan.finals.dal.mapper.GoodsSecondsKillMapper;
import com.xuleyan.finals.dal.pojo.Account;
import com.xuleyan.finals.dal.pojo.AccountCriteria;
import com.xuleyan.finals.dal.pojo.GoodsSecondsKill;
import com.xuleyan.finals.service.api.AccountService;
import com.xuleyan.frame.extend.redis.jedis.JedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 * @author xuleyan
 * @version AccountServiceImpl.java, v 0.1 2021-07-16 11:02 下午
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountExtMapper accountExtMapper;

    @Resource
    private GoodsSecondsKillMapper goodsSecondsKillMapper;

    @Resource
    private JedisTemplate jedisTemplate;

    @Override
    public Account findOne(Integer id) {
        log.info("查询数据库 >> findOne >> id = {}", id);
        AccountCriteria example = new AccountCriteria();
        example.createCriteria().andIdEqualTo(id);
        List<Account> accounts = accountExtMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(accounts)) {
            return accounts.get(0);
        }
        return null;
    }

    @Override
    public int subGoods(Integer id) {
        return accountExtMapper.updateById(id);
    }

    @Override
    public int insertGoods(String userId) {
        GoodsSecondsKill record = new GoodsSecondsKill();
        record.setUserId(userId);
        record.setGmtCreate(new Date());
        return goodsSecondsKillMapper.insert(record);
    }

    /**
     * 判断操作是否成功， 成功返回true, 失败返回false
     * @param id
     * @param requestId
     * @return
     */
    @Override
    @Transactional(value = "xlyTransactionManager",rollbackFor = Exception.class)
    public boolean insertAndSubGoods(Integer id, String requestId) {
        try {
            int insertRows = insertGoods(requestId);
            int affectRows = subGoods(id);
            log.info("insertRows = {}, affectRows = {}", insertRows, affectRows);
            if (insertRows != 1 || affectRows != 1) {
                // 出现异常，insertRows !=1 可能是插入了重复的用户，需要回滚
                // affectRows != 1 可能是商品已经为0了，需要回滚
                Account account = findOne(id);
                // 重新设置redis中的值
                jedisTemplate.setEx(AccountConstants.GOODS_KEY, account.getGoods().toString(), AccountConstants.GOODS_TIME);
                log.error("出现异常, 重新更新redis中的值, goods = {}", account.getGoods());
                throw new RuntimeException("异常");
            }
            return true;
        } catch (Exception e) {
            log.error("异常,事务回滚", e);
            throw e;
        }
    }
}
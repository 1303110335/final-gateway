/**
 * xuleyan.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.service.api;

import com.xuleyan.finals.dal.pojo.Account;
import com.xuleyan.finals.dal.pojo.GoodsSecondsKill;
import com.xuleyan.finals.service.api.param.GoodsParam;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author xuleyan
 * @version AccountService.java, v 0.1 2021-07-16 11:01 下午
 */
public interface AccountService {

    public Account findOne(Integer id);

    int subGoods(Integer id);

    /**
     * 插入秒杀记录
     * @param userId
     * @return
     */
    int insertGoods(String userId);

    /**
     * 获取秒杀记录
     * @param userId
     * @return
     */
    GoodsSecondsKill findGoods(String userId);

    /**
     * 插入秒杀记录并更新redis
     * 假设插入提现记录，扣减用户余额
     * @param goodsParam
     * @return
     */
    @Transactional(value = "xlyTransactionManager",rollbackFor = Exception.class)
    boolean insertAndSubGoods(GoodsParam goodsParam);
}
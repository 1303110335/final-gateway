/**
 * xuleyan.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.service.api;

import com.xuleyan.finals.dal.pojo.Account;

/**
 *
 * @author xuleyan
 * @version AccountService.java, v 0.1 2021-07-16 11:01 下午
 */
public interface AccountService {

    public Account findOne(Integer id);

    int subGoods(Integer id);

    int insertGoods(String userId);

    boolean insertAndSubGoods(Integer id, String requestId);
}
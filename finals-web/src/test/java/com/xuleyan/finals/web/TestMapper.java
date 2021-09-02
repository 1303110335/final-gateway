/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web;

import com.xuleyan.finals.dal.mapper.AccountMapper;
import com.xuleyan.finals.dal.pojo.Account;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 *
 * @author xuleyan
 * @version TestMapper.java, v 0.1 2021-07-26 10:29 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class TestMapper {

    @Resource
    private AccountMapper accountMapper;

    @Test
    @Transactional(value = "xlyTransactionManager", rollbackFor = Exception.class)
    public void testFirstCache() {

        // 开启事务就会使用缓存中的数据，而不是再次去数据库中查找

        Account account = accountMapper.selectByPrimaryKey(1);
        System.out.println(account);
        log.info("account.age = {}", account.getAge());
        account.setAge(1000);
        Account account2 = accountMapper.selectByPrimaryKey(1);
        System.out.println(account2);

//        Account accountUpdate = new Account();
//        accountUpdate.setAddress("zhejiang");
//        accountUpdate.setId(1);
//        int i = accountMapper.updateByPrimaryKey(accountUpdate);
//        log.info("update result = {}", i);

        Account account3 = accountMapper.selectByPrimaryKey(1);
        System.out.println(account3);
    }
}
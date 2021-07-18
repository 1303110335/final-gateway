/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.config;

import com.xuleyan.frame.extend.lock.DistributedLock;
import com.xuleyan.frame.extend.lock.ZookeeperDistributedLock;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 *
 * @author xuleyan
 * @version ZookeeperConfig.java, v 0.1 2021-07-17 5:47 下午
 */
@Configuration
public class ZookeeperConfig implements DisposableBean {

    private CuratorFramework client = null;
    private DistributedLock distributedLock = null;

    @Bean(value = "distributedLock")
    @ConditionalOnMissingBean
    public DistributedLock distributedLock() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(100, 3);
        client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
        client.start();
        distributedLock = new ZookeeperDistributedLock(client, "/curator");
        return distributedLock;
    }

    @Override
    public void destroy() throws Exception {
        client.close();
    }
}
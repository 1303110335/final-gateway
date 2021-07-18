/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.task;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.xuleyan.finals.common.constants.AccountConstants;
import com.xuleyan.frame.extend.redis.jedis.JedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用canal 监听mysql binlog 同步redis数据
 * @author xuleyan
 * @version RedisUpdateTask.java, v 0.1 2021-07-18 4:09 下午
 */
//@Component
@Slf4j
public class RedisUpdateTask {

    @Resource
    private JedisTemplate jedisTemplate;

    @PostConstruct
    public void subscribe() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                doSubscribe();
            }
        });
    }

    /**
     * 订阅mysqlbinlog日志
     */
    private void doSubscribe() {
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(AddressUtils.getHostIp(), 11111), "example", "", "");
        int batchSize = 1000;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            while (true) {
                // 获取指定数量的数据
                Message message = connector.getWithoutAck(batchSize);
                long batchId = message.getId();
                int size = message.getEntries().size();
                try {
                    if (batchId == -1 || size == 0) {
//                        log.info("sleep 100 ms...");
                        Thread.sleep(100);
                    } else {
                        updateHandler(message.getEntries());
                    }
                    connector.ack(batchId);
                } catch (InterruptedException e) {
                    log.error("更新操作异常", e);
                    connector.rollback(batchId);
                }
            }

        } finally {
            connector.disconnect();
        }
    }

    /**
     * 进行业务操作
     * @param entries
     */
    private void updateHandler(List<CanalEntry.Entry> entries) {
        for (CanalEntry.Entry entry : entries) {
            // 跳过事务开启和结束
            if (entry.getEntryType().equals(CanalEntry.EntryType.TRANSACTIONBEGIN) ||
                    entry.getEntryType().equals(CanalEntry.EntryType.TRANSACTIONEND)) {
                continue;
            }

            CanalEntry.RowChange rowChage = null;
            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(), e);
            }
            CanalEntry.EventType eventType = rowChage.getEventType();
            log.info("================> binlog : logFileName={}, logFileOffset={}, schemaName={}, tableName={}, eventType={}",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(), eventType);

            for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == CanalEntry.EventType.DELETE) {
                    // 数据删除
                    log.info("数据删除...");
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    // 数据添加
                    log.info("数据添加...");
                } else {
                    // 数据修改
//                    System.out.println("-------> before");
//                    printColumn(rowData.getBeforeColumnsList());
//                    System.out.println("-------> after");
                    redisUpdate(rowData.getAfterColumnsList(), entry.getHeader().getTableName());
                }
            }
        }
    }

    private void redisUpdate(List<CanalEntry.Column> columns, String tableName) {
        for (CanalEntry.Column column : columns) {
            String key = column.getName();
            String value = column.getValue();
            if (tableName.equals(AccountConstants.GOODS_TABLE) && key.equals(AccountConstants.GOODS_FIELD)) {
                log.info("更新redis中的goods, key = {}, value = {}", AccountConstants.GOODS_KEY, value);
                jedisTemplate.setEx(AccountConstants.GOODS_KEY, value, AccountConstants.GOODS_TIME);
            }
        }
    }

    private static void printColumn(List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }

}
/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.controller;

import com.xuleyan.finals.common.constants.AccountConstants;
import com.xuleyan.finals.dal.pojo.Account;
import com.xuleyan.finals.service.api.AccountService;
import com.xuleyan.frame.common.util.BQSnowFlakeUtils;
import com.xuleyan.frame.extend.lock.DistributedLock;
import com.xuleyan.frame.extend.redis.jedis.JedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author xuleyan
 * @version AccountController.java, v 0.1 2021-07-16 11:01 下午
 */
@RestController
@RequestMapping("account")
@Slf4j
public class AccountController {

    @Resource
    private AccountService accountService;

    @Resource
    private JedisTemplate jedisTemplate;

    @Resource
    private DistributedLock distributedLock;

    private ThreadLocalRandom random = ThreadLocalRandom.current();

    private volatile AtomicInteger errNumber = new AtomicInteger(1);

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    /**
     * 模拟两个线程同时进入数据库操作方法
     * @return
     */
    @RequestMapping("/getGoods2")
    public String getGoods2() {
        Integer id = 1;

        String requestId = BQSnowFlakeUtils.generate();
        accountService.insertAndSubGoods(id, requestId);

        return "success";
    }

    @RequestMapping("/zookeeper")
    public String getGoodsZookeeperLock() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        boolean success = false;
        String lockKey = "lock-key1";
        try {
            for (int i = 0; i < 10; i ++) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            distributedLock.tryLock(lockKey, 10000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //distributedLock.unlock(lockKey);
                    }
                });

            }
        } catch (Exception e) {
            log.info("获取锁失败【前方排队拥挤，请重试】");
            return "【前方排队拥挤，请重试】";
        }
        return success ? "success" : "fail";
    }

    /**
     * redis 分布式锁
     * 开始：16:10:19.280
     * 结束：16:10:56.432
     * 27s 300
     *
     * start: 16:29:53.795
     * end  : 16:29:55.863
     *
     * start : 17:20:24.320
     * end   : 17:20:38.846
     *
     * 220 7秒 1秒=32个
     * start : 17:29:41.878
     * end   : 17:29:48.756
     *
     * 300 8s : 1s 37个
     * start : 17:37:08.925
     * end   : 17:37:16.750
     *
     * 300 6s : 1s 50个
     * 开始 ： 12:00:46.169
     * 结束 ： 12:00:52.140
     *
     * zookeeper lock 无等待情况下
     *
     * 20 个线程竞争 2组
     * 成功26个  成功率 0.65
     * 失败14个
     *
     * 40个线程竞争 2组
     * 失败 49
     * 成功 31个 成功率 0.3875
     *
     * 80个线程竞争 2组
     * 失败 101个
     * 成功 59 个 成功率 0.36875
     *
     * 160个线程竞争 2组
     * 失败 300个
     * 成功 20个  成功率 0.0625
     *
     * 320个线程竞争 2组
     * 失败 629个
     * 成功 11个  成功率 0.0171875
     *
     * 640个线程竞争 2组
     * 失败 1271个
     * 成功 9个  成功率 0.007
     *
     * zookeeper 等待200ms情况下
     * 20 个线程竞争 2组
     * 成功40个  成功率 1
     * 失败0个
     *
     * 40 个线程竞争 2组
     * 成功80个  成功率 1  用时1秒
     * 失败0个
     * 11:46:41.631
     * 11:46:42.683
     *
     * 80 个线程竞争 2组
     * 成功54个  成功率 1  用时2秒
     * 失败106个
     *
     * 160 个线程竞争 2组
     * 成功32个  成功率 1  用时2秒
     * 失败288个
     *
     * 800个线程 10组
     * 成功 177
     * 失败 7847
     * 开始： 12:10:47.040 耗时25s
     * 结束： 12:11:12.001
     *
     * 第一秒进入的线程请求近 1000个
     * 加一个RateLimiter ,限制每秒300个
     *
     * redis与数据库不一致，怎么办？
     *  一方面redis设置有过期时间3s，只会导致短时间的不一致，最终一致性还是可以保证的
     *  另一方面，最终还是以mysql中的数据为最终依据，当redis中还有值，但是mysql中没有了，就会更新mysql中的值到redis，来保证数据一致性
     *  redis本身就无法保证完全的任何时刻的一致性，因为它实现的是ap，通过保证可用性和分区容错性而牺牲绝对一致性，
     *      在高并发的情况下还是要选用redis，然后通过其他方案来保证最终一致性
     *
     *  redis分布式锁，还有可能会因为节点挂掉而未同步到从节点而导致分布式锁同时被两个线程同时获取到，
     *      此时就要从程序方面另外去保证数据的一致性，mysql更新失败，首先会查询数据库中的redis值更新到redis，然后会抛出异常，回滚事务
     *
     *     经验： 可以提前设置商品数量到redis中，减少一开始访问查询数据库过多而将数据库连接占满
     *
     *  zookeeper分布式锁，在高并发场景下效率较低，由于创建节点和删除节点都比较消耗资源，适用于高可靠而并发亮不是很大的场景
     *
     * @return
     */
    @RequestMapping("/getGoods")
    public String getGoods() {

        String key = AccountConstants.GOODS_KEY;
        String lockKey = "lock-key";
        Integer id = 1;

        String goodsNumStr = jedisTemplate.get(key);
        Integer goodsNum = 0;
        if (goodsNumStr == null) {
            Account account = accountService.findOne(id);
            goodsNum = account.getGoods();
            jedisTemplate.setEx(key, goodsNum.toString(), AccountConstants.GOODS_TIME);
        } else {
            goodsNum = Integer.parseInt(goodsNumStr);
        }

        String requestId = BQSnowFlakeUtils.generate();
        if (goodsNum > 0) {
            boolean success = jedisTemplate.setIfAbsent(lockKey, requestId, 1);
//            boolean success = false;
//            try {
//                // 获取锁并等待1s
//                success = distributedLock.tryLock(lockKey, 200);
//            } catch (Exception e) {
//                log.info("获取锁失败【前方排队拥挤，请重试】");
//                return "【前方排队拥挤，请重试】";
//            }
            if (success) {
                log.info("获取锁成功");
                try {
                    goodsNumStr = jedisTemplate.get(key);
                    goodsNumStr = goodsNumStr == null ? "0" : goodsNumStr;
                    if (Integer.parseInt(goodsNumStr) > 0) {
                        goodsNum = Integer.parseInt(goodsNumStr);
                        accountService.insertAndSubGoods(id, requestId);
//                        if (goodsNum == 100 && errNumber.getAndDecrement() > 0) {
//                            // @TODO 模拟机器宕机,出现异常, 需要有补偿方案
//                            int b  = 0 ;
//                            int num = 100 / b;
//                        }
                        log.info("当前商品数量 : {} ,商品数量减一", goodsNum);
                        //jedisTemplate.increBy(key, -1L);
                    }
                } finally {
                    removeLockKey(lockKey, requestId);
//                    try {
//                        distributedLock.unlock(lockKey);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    log.info("释放锁");
                }
            } else {
                log.info("获取锁失败【前方排队拥挤，请重试】");
                return "【前方排队拥挤，请重试】";
            }
        } else {
            log.info("商品数量为0");
        }

        return "success";
    }

    /**
     * 尝试使用canal 同步binlog删除缓存方案
     * @return
     */
    public String getGoodsCanal() {
        return "success";
    }


    /**
     * lua脚本删除key
     * @param key
     * @param requestId
     * @return
     */
    private boolean removeLockKey(String key, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedisTemplate.eval(script, Collections.singletonList(key), Collections.singletonList(requestId));
        if ("1".equals(result)) {
            return true;
        }
        return false;
    }

}
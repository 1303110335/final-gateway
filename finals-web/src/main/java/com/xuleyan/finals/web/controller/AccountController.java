/**
 * xuleyan.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.controller;

import com.alibaba.fastjson.JSON;
import com.xuleyan.finals.common.constants.AccountConstants;
import com.xuleyan.finals.common.constants.TopicConstants;
import com.xuleyan.finals.dal.pojo.Account;
import com.xuleyan.finals.service.api.AccountService;
import com.xuleyan.finals.service.api.param.GoodsParam;
import com.xuleyan.finals.web.annotation.Encryption;
import com.xuleyan.finals.web.annotation.GoodsLimiter;
import com.xuleyan.finals.web.domain.User;
import com.xuleyan.finals.web.transaction.GoodsProducer;
import com.xuleyan.finals.web.transaction.GoodsTransactionListener;
import com.xuleyan.frame.common.util.BQSnowFlakeUtils;
import com.xuleyan.frame.extend.lock.DistributedLock;
import com.xuleyan.frame.extend.redis.jedis.JedisTemplate;
import com.xuleyan.frame.mask.annotation.Masking;
import com.xuleyan.provider.facade.GoodsFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author xuleyan
 * @version AccountController.java, v 0.1 2021-07-16 11:01 下午
 */
@RestController
@RequestMapping(value = "account", produces = "text/plain;charset=utf-8")
@Slf4j
public class AccountController {

    @Resource
    private AccountService accountService;

    @Resource
    private JedisTemplate jedisTemplate;

    @Resource
    private DistributedLock distributedLock;

    @Resource
    private GoodsTransactionListener goodsTransactionListener;

    private ThreadLocalRandom random = ThreadLocalRandom.current();

    private volatile AtomicInteger errNumber = new AtomicInteger(1);

    @Reference(check = false)
    private GoodsFacade goodsFacade;

    @RequestMapping("/goodsDubbo")
    public String goodsDubbo() {
        String result = "服务器开了个小差~";
        try {
            CompletableFuture<String> future = RpcContext.getContext().asyncCall(() -> goodsFacade.consumeGoods("haha"));
            log.info("result = {}", future.get());
            return "this is " + future.get();
        } catch (Exception ex) {
            log.error("服务器异常", ex);
        }

        return result;

    }

    @GetMapping("/asyncHello")
    public String asyncHello() {
        CompletableFuture<String> result = goodsFacade.sayHello("haha");
        AtomicReference<String> s = new AtomicReference<>();
//        try {
//            s = result.get();
//            log.info("result = {}", s);
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
        result.whenComplete((v, t) -> {
            if (t != null) {
                t.printStackTrace();
            } else {
                s.set(v);
                // 后输出
                log.info("response : {}", v);
            }
        });
        // 早于结果输出
        log.info("end response : {}", s.get());
        return s.get();
    }

    @GetMapping("/hello")
//    @Encryption
//    public String hello(@RequestBody User user) {
    public String hello() {
        // 线程复用，threadLocal值不清除，会一直存在并复用
        String result = goodsFacade.consumeGoods("haha");

        log.info("result = {}", result);
        return result;
    }

    @RequestMapping("/test")
    public String test(@RequestParam String name) {
        return name + "test";
    }


    @RequestMapping("/list")
    @Encryption
    @Masking
    public Object getList(@RequestBody User user) {
        //System.out.println("运行程序: getList");

        List<User> accountList = new ArrayList<>();
        accountList.add(user);
        return JSON.toJSONString(accountList);
    }

    @RequestMapping(value = "/show")
    public String showGoods() {
        Account account = accountService.findOne(1);
        return JSON.toJSONString(account);
    }

    /**
     * 模拟两个线程同时进入数据库操作方法
     * 全靠mysql干活测试
     *
     * @return
     */
    @RequestMapping("/getGoods2")
    public String getGoods2() {
        Integer id = 1;
        Account account = accountService.findOne(id);
        log.info("account数量={}", account.getGoods());
        if (account.getGoods() > 0) {
            String requestId = BQSnowFlakeUtils.generate();
            GoodsParam goodsParam = new GoodsParam();
            goodsParam.setId(id);
            goodsParam.setRequestId(requestId);
            try {
                accountService.insertAndSubGoods(goodsParam);
            } catch (Exception e) {
                log.info("获取锁失败【前方排队拥挤，请重试】");
                return "fail";
            }
            return "success";
        } else {
            log.info("获取锁失败【前方排队拥挤，请重试】");
            return "fail";
        }
    }

    @RequestMapping("getGoodsByRedisAndMq")
    public String getGoodsByRedisAndMq() {
        // 减一操作，当数量为0，减一为-1
        // 当key不存在，减一为-1
        String requestId = BQSnowFlakeUtils.generate();
        boolean send = GoodsProducer.send(TopicConstants.GOODS_TOPIC, requestId);
        String message = send ? "成功" : "失败";
        log.info("发送消息{}, requestId = {}", message, requestId);
        return "success";
    }

    @RequestMapping("/zookeeper")
    public String getGoodsZookeeperLock() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        boolean success = false;
        String lockKey = "lock-key1";
        try {
            for (int i = 0; i < 10; i++) {
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
     * <p>
     * start: 16:29:53.795
     * end  : 16:29:55.863
     * <p>
     * start : 17:20:24.320
     * end   : 17:20:38.846
     * <p>
     * 220 7秒 1秒=32个
     * start : 17:29:41.878
     * end   : 17:29:48.756
     * <p>
     * 300 8s : 1s 37个
     * start : 17:37:08.925
     * end   : 17:37:16.750
     * <p>
     * 300 6s : 1s 50个
     * 开始 ： 12:00:46.169
     * 结束 ： 12:00:52.140
     * <p>
     * zookeeper lock 无等待情况下
     * <p>
     * 20 个线程竞争 2组
     * 成功26个  成功率 0.65
     * 失败14个
     * <p>
     * 40个线程竞争 2组
     * 失败 49
     * 成功 31个 成功率 0.3875
     * <p>
     * 80个线程竞争 2组
     * 失败 101个
     * 成功 59 个 成功率 0.36875
     * <p>
     * 160个线程竞争 2组
     * 失败 300个
     * 成功 20个  成功率 0.0625
     * <p>
     * 320个线程竞争 2组
     * 失败 629个
     * 成功 11个  成功率 0.0171875
     * <p>
     * 640个线程竞争 2组
     * 失败 1271个
     * 成功 9个  成功率 0.007
     * <p>
     * zookeeper 等待200ms情况下
     * 20 个线程竞争 2组
     * 成功40个  成功率 1
     * 失败0个
     * <p>
     * 40 个线程竞争 2组
     * 成功80个  成功率 1  用时1秒
     * 失败0个
     * 11:46:41.631
     * 11:46:42.683
     * <p>
     * 80 个线程竞争 2组
     * 成功54个  成功率 1  用时2秒
     * 失败106个
     * <p>
     * 160 个线程竞争 2组
     * 成功32个  成功率 1  用时2秒
     * 失败288个
     * <p>
     * 800个线程 10组
     * 成功 177
     * 失败 7847
     * 开始： 12:10:47.040 耗时25s
     * 结束： 12:11:12.001
     * <p>
     * <p>
     * rocketmq 分布式消息  11s处理 300个
     * 800个线程 10组
     * 开始：10:58:36.568
     * 结束：10:58:47.878
     * <p>
     * 加上rateLimiter 灵牌桶算法 11s 处理300个
     * 800个线程 10组
     * 开始：15:23:49.608
     * 结束：15:23:58.760
     *
     * <p>
     * 第一秒进入的线程请求近 1000个
     * 加一个RateLimiter ,限制每秒300个
     * <p>
     * redis与数据库不一致，怎么办？
     * 一方面redis设置有过期时间3s，只会导致短时间的不一致，最终一致性还是可以保证的
     * 另一方面，最终还是以mysql中的数据为最终依据，当redis中还有值，但是mysql中没有了，就会更新mysql中的值到redis，来保证数据一致性
     * redis本身就无法保证完全的任何时刻的一致性，因为它实现的是ap，通过保证可用性和分区容错性而牺牲绝对一致性，
     * 在高并发的情况下还是要选用redis，然后通过其他方案来保证最终一致性
     * <p>
     * redis分布式锁，还有可能会因为节点挂掉而未同步到从节点而导致分布式锁同时被两个线程同时获取到，
     * 此时就要从程序方面另外去保证数据的一致性，mysql更新失败，首先会查询数据库中的redis值更新到redis，然后会抛出异常，回滚事务
     * <p>
     * 经验： 可以提前设置商品数量到redis中，减少一开始访问查询数据库过多而将数据库连接占满
     * <p>
     * zookeeper分布式锁，在高并发场景下效率较低，由于创建节点和删除节点都比较消耗资源，适用于高可靠而并发量不是很大的场景
     *
     * @return
     */
    @GoodsLimiter(limit = 300)
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
            // 方案一：redis锁
            boolean success = jedisTemplate.setIfAbsent(lockKey, requestId, 1);
            // 方案二：zookeeper锁
            //boolean success = getZookeeperLock(lockKey);
            if (success) {
                log.info("获取锁成功");
                try {
                    goodsNumStr = jedisTemplate.get(key);
                    goodsNumStr = goodsNumStr == null ? "0" : goodsNumStr;
                    if (Integer.parseInt(goodsNumStr) > 0) {
                        goodsNum = Integer.parseInt(goodsNumStr);

                        GoodsParam goodsParam = new GoodsParam();
                        goodsParam.setId(id);
                        goodsParam.setRequestId(requestId);
                        // 方案一：直接操作数据库，然后redis值递减一
                        accountService.insertAndSubGoods(goodsParam);

                        // 方案二：通过事务消息来实现发送消息和本地事务执行的一致性
//                        GoodsProducer.setTransactionListener(goodsTransactionListener);
//                        GoodsProducer.sendTransMessage(TopicConstants.GOODS_TOPIC, requestId, goodsParam);

                        doSomethingError(goodsNum);
                        log.info("当前商品数量 : {} ,商品数量减一", goodsNum);
                        jedisTemplate.increBy(key, -1L);
                    }
                } catch (Exception ex) {
                    log.error("操作mysql减库存或redis减库存失败", ex);
                } finally {
                    // 方案一：redis解锁
                    removeLockKey(lockKey, requestId);
                    // 方案二：zookeeper解锁
                    //removeZookeeperLock(lockKey);
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
     * 用消息来实现分布式事务
     */
    @RequestMapping("mqgoods")
    public void getGoodsByMq() {

        Integer id = 1;
        String requestId = BQSnowFlakeUtils.generate();
        GoodsParam goodsParam = new GoodsParam();
        goodsParam.setId(id);
        goodsParam.setRequestId(requestId);

        // 确保发送消息和本地事务同时成功
        GoodsProducer.setTransactionListener(goodsTransactionListener);
        GoodsProducer.sendTransMessage(TopicConstants.GOODS_TOPIC, requestId, goodsParam);
        // 告诉第三方去扣减用户银行卡的余额
    }

    /**
     * 让服务器在某种情况下出错,模拟异常
     *
     * @param goodsNum
     */
    private void doSomethingError(Integer goodsNum) {
        if (goodsNum == 100 && errNumber.getAndDecrement() > 0) {
            // @TODO 模拟机器宕机,出现异常, 需要有补偿方案
            int b = 0;
            int num = 100 / b;
        }
    }

    private void removeZookeeperLock(String lockKey) {
        try {
            distributedLock.unlock(lockKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean getZookeeperLock(String lockKey) {
        boolean success = false;
        try {
            // 获取锁并等待1s
            success = distributedLock.tryLock(lockKey, 200);
            return success;
        } catch (Exception e) {
            log.info("获取锁失败【前方排队拥挤，请重试】");
            return false;
        }
    }

    /**
     * 尝试使用canal 同步binlog删除缓存方案
     *
     * @return
     */
    public String getGoodsCanal() {
        return "success";
    }


    /**
     * lua脚本删除key
     *
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
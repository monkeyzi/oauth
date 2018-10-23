package com.monkeyzi.oauth.common;

import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.ZParams;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author 高艳国
 * @date 2018/10/23 16:42
 * @description  redis实现限流
 **/
@Component
@Slf4j
public class RedisRaterLimiter {

    @Resource
    private JedisPool jedisPool;


    private static final String BUCKET = "BUCKET_";
    private static final String BUCKET_COUNT = "BUCKET_COUNT";
    private static final String BUCKET_MONITOR = "BUCKET_MONITOR_";


    public String acquireTokenFromBucket(String point, int limit, long timeout) {
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            String token=UUID.randomUUID().toString().replace("-","");
            long now=System.currentTimeMillis();
            //开启redis事务
            Transaction transaction=jedis.multi();

            //删除信号量 移除有序集中指定区间(score)内的所有成员 ZREMRANGEBYSCORE key min max
            transaction.zremrangeByScore((BUCKET_MONITOR + point).getBytes(), "-inf".getBytes(), String.valueOf(now - timeout).getBytes());
            //为每个有序集分别指定一个乘法因子(默认设置为 1) 每个成员的score值在传递给聚合函数之前都要先乘以该因子
            ZParams params = new ZParams();
            params.weightsByDouble(1.0, 0.0);
            //计算给定的一个或多个有序集的交集
            transaction.zinterstore(BUCKET + point, params, BUCKET + point, BUCKET_MONITOR + point);

            //计数器自增
            transaction.incr(BUCKET_COUNT);
            List<Object> results = transaction.exec();
            long counter = (Long) results.get(results.size() - 1);

            transaction = jedis.multi();
            //Zadd 将一个或多个成员元素及其分数值(score)加入到有序集当中
            transaction.zadd(BUCKET_MONITOR + point, now, token);
            transaction.zadd(BUCKET + point, counter, token);
            transaction.zrank(BUCKET + point, token);
            results = transaction.exec();
            //获取排名，判断请求是否取得了信号量
            long rank = (Long) results.get(results.size() - 1);

            if (rank < limit) {
                return token;
            } else {
                //没有获取到信号量，清理之前放入redis中垃圾数据
                transaction = jedis.multi();
                //Zrem移除有序集中的一个或多个成员
                transaction.zrem(BUCKET_MONITOR + point, token);
                transaction.zrem(BUCKET + point, token);
                transaction.exec();
            }

        }catch (Exception e){
            log.error("redis限流出错 e={}",e.toString());
            throw  new BusinessException(ErrorCodeEnum.GL10004);
        }finally {
            if (jedis!=null){
                jedis.close();
            }
        }
        return null;
    }



}

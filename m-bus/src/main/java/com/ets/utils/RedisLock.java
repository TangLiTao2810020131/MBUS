package com.ets.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author xuqiang
 * @date 2019/8/1 16:16
 */
@Service
public class RedisLock {
    private static final Logger log = LoggerFactory.getLogger(RedisLock.class);
    private static final long LOCK_TRY_INTERVAL = 50L;// 默认多久尝试获取一次锁

    private static final long LOCK_TRY_TIMEOUT = 200L;// 默认尝试时间

    private static final long DEFAULT_EXPIRE_TIME = 3000L;   // 默认key过期时间

    private static final String LOCK_SUCCESS = "OK";    // set方法执行成功后的返回值

    @Resource
    private JedisPool jedisPool;

    private Jedis jedis;

    public Jedis getJedis() {
        if (jedis == null) {
            jedis = jedisPool.getResource();
        }
        return jedis;
    }

    /**
     * 尝试获取全局锁
     *
     * @param key 锁名
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(String key,String value) {
        return getLock(key, value, LOCK_TRY_TIMEOUT, LOCK_TRY_INTERVAL, DEFAULT_EXPIRE_TIME);
    }

    /**
     * 尝试获取全局锁
     *
     * @param key     锁名
     * @param timeout 获取超时时间 单位ms
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(String key,String value, long timeout) {
        return getLock(key, value, timeout, LOCK_TRY_INTERVAL, DEFAULT_EXPIRE_TIME);
    }

    /**
     * 尝试获取全局锁
     *
     * @param key         锁名
     * @param timeout     获取锁的超时时间
     * @param tryInterval 多少毫秒尝试获取一次
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(String key,String value, long timeout, long tryInterval) {
        return getLock(key, value, timeout, tryInterval, DEFAULT_EXPIRE_TIME);
    }

    /**
     * 尝试获取全局锁
     *
     * @param key            锁名
     * @param timeout        获取锁的超时时间
     * @param tryInterval    多少毫秒尝试获取一次
     * @param lockExpireTime 锁的过期
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(String key, String value,long timeout, long tryInterval, long lockExpireTime) {
        return getLock(key, value, timeout, tryInterval, lockExpireTime);
    }

    /**
     * 尝试获取全局锁, 只尝试一次
     *
     * @param key 锁名
     * @return true 获取成功，false获取失败
     */
    public boolean onceTryLock(String key,String value) {
        return getLock(key, value, DEFAULT_EXPIRE_TIME);
    }

    /**
     * 尝试获取全局锁, 只尝试一次
     *
     * @param key            锁名
     * @param lockExpireTime 锁的过期
     * @return true 获取成功，false获取失败
     */
    public boolean onceTryLock(String key,String value, long lockExpireTime) {
        return getLock(key, value, lockExpireTime);
    }

    /**
     * 获取全局锁
     *
     * @param key         锁名
     * @param value       锁value, 如果要保证加锁和解锁是同一个客户端的话, 这个参数用来指定特定客户端
     * @param expireTime  锁的超时时间
     * @param timeout     获取锁的超时时间
     * @param tryInterval 多少ms尝试一次
     * @return
     */
    public boolean getLock(String key, String value, long timeout, long tryInterval, long expireTime) {
        try  {
            // 锁如果为空, 获取锁失败
            jedis = getJedis();
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
                return false;
            }
            long startTime = System.currentTimeMillis();  // 开始时间戳
            do {
                String result = jedis.set(key, value, "NX", "PX", expireTime);
                if (LOCK_SUCCESS.equals(result)) {  // 返回成功，表示加锁成功
                    return true;
                }
                if (System.currentTimeMillis() - startTime > timeout) { // 尝试超过了设定超时时间后直接跳出循环，获取锁失败
                    log.info("获取锁超时: {}", System.currentTimeMillis() - startTime);
                    return false;
                }
                Thread.sleep(tryInterval);  // 循环时设置时间差
            }
            while (true);   // 只要锁存在，循环
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 获取全局锁(无超时后循环重试机制，拿不到直接返回false)
     *
     * @param key        锁名
     * @param value      锁value
     * @param expireTime 超时时间
     * @return
     */
    public boolean getLock(String key, String value, long expireTime) {
        try {
             jedis = getJedis();
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
                return false;
            }
            // 参数: key, value, key不存在set操作存在就不做任何操作, 可设置超时时间, 具体超时时间
            String result = jedis.set(key, value, "NX", "PX", expireTime);
            if (LOCK_SUCCESS.equals(result)) {  // 返回成功，表示加锁成功
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return false;
    }

    /**
     * 释放锁
     *
     * @param key 锁名
     */
    public void releaseLock(String key) {
        try {
             jedis = getJedis();
            if (!StringUtils.isEmpty(key)) {
                Long del = jedis.del(key);
                log.info("锁名：{}，是否释放成功：{}", key, del);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }



}

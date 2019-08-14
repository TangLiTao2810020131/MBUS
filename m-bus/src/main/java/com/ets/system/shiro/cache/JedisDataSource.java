package com.ets.system.shiro.cache;

import redis.clients.jedis.Jedis;

/**
 * @author 吴浩
 * @create 2019-01-08 14:44
 */
public interface JedisDataSource {
    Jedis getRedisClient();
    void returnResource(Jedis jedis);
    void returnResource(Jedis jedis, boolean broken);

}

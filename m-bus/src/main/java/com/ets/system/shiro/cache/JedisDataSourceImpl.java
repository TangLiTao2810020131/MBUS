package com.ets.system.shiro.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author 吴浩
 * @create 2019-01-08 14:44
 */
@Service
public class JedisDataSourceImpl implements JedisDataSource{

    @Autowired
    private JedisPool jedisPool;

    @Override
    public Jedis getRedisClient() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis;
        } catch (Exception e) {
            if (null != jedis)
                jedis.close();
        }
        return null;
    }

    @Override
    public void returnResource(Jedis jedis) {
        jedis.close();
    }

    @Override
    public void returnResource(Jedis jedis, boolean broken) {
        jedis.close();
    }

}

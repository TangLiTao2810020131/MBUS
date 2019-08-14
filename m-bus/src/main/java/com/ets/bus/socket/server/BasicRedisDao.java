package com.ets.bus.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class BasicRedisDao {
    private static final Logger log = LoggerFactory.getLogger(BasicRedisDao.class);

    @Resource
    private JedisPool jedisPool;
    private Jedis jedis;
    private static Map<String, String> map;
    /**
     * 获取jedis
     * @return
     */
    public Jedis getJedis(){
        if (jedis == null) {
            jedis = jedisPool.getResource();
        }
        return jedis;
    }

    /**
     * 清空数据库
     */
    public void flushDB(){
        jedis = getJedis();
        if (jedis !=null ) {
            jedis.flushDB();
        }
    }
    /**
     * 保存string的数据
     */
    public  void set(String key,String value){
        jedis = getJedis();
        if (jedis!=null) {
            jedis.set(key, value,"NX","PX",10000L);
        }
    }
    /**
     * 删除某个键
     */
    public  void del(String key){
        jedis = getJedis();
        if (jedis!=null) {
            jedis.del(key);
        }
    }

    /**
     * 获取hash中某个field的值
     */
    public  String get(String key){
        jedis = getJedis();
        if (jedis !=null) {
            return jedis.get(key);
        }
        return null;
    }

    /**
     * 设置某个带时间的key
     */
    public  void saveTimeKey(String key,String value,int seconds){
        jedis = getJedis();
        if (jedis!=null) {
            jedis.setex(key, seconds, value);
        }
    }
    /**
     * 查询某个key是否存在
     */
    public  Boolean existKey(String key){
        jedis = getJedis();
        if (jedis!=null) {
            return jedis.exists(key);
        }else {
            return null;
        }
    }
    /**
     * 更新某个key并返回原来的值
     */
    public  String updateKey(String key,String value){
        jedis = getJedis();
        if (jedis!=null) {
            String old = jedis.getSet(key, value);
            return old;
        }
        return null;
    }
    /**
     * 往某个hash表中存放数据
     */
    public  void hset(String key,String field,String value){
        jedis = getJedis();
        if (jedis != null) {
            jedis.hset(key, field, value);
        }
    }
    /**
     * 获取某个hash中的所有filed和value
     */
    public  Map<String, String> getHashValues(String key){
        jedis = getJedis();
        map = new HashMap<String, String>();
        if (jedis != null ) {
            map = jedis.hgetAll(key);
        }
        return map;
    }
    /**
     * 判断hash中某个field是否已经存在
     */
    public  Boolean existsField(String key,String field){
        jedis = getJedis();
        Boolean hexists = null;
        if (jedis != null) {
            hexists = jedis.hexists(key, field);
        }
        return  hexists;
    }
    /**
     * 删除hash中的某个field
     */
    public  void removeField(String key,String field){
        jedis = getJedis();
        if (jedis!=null) {
            jedis.hdel(key, field);
        }
    }
    /**
     * 获取hash中某个field的值
     */
    public  String getHashFieldValue(String key,String field){
        jedis = getJedis();
        if (jedis !=null) {
            return jedis.hget(key, field);
        }
        return null;
    }
    /**
     * 查询hash中的所有filed
     */
    public Set<String> getFields(String key){
        jedis = getJedis();
        if (jedis != null ) {
            return jedis.hkeys(key);
        }
        return null;
    }
    /**
     * 获取key中一个或多个field的值
     */
    public List<String> hmgets(String key, String fields[]){
        jedis = getJedis();
        if (jedis != null && fields != null && fields.length >0) {
            return jedis.hmget(key, fields);
        }
        return null;
    }
    /**
     * 同时存入多个field到某个key中
     */
    public  void hmset(String key,Map<String, String> map){
        jedis = getJedis();
        if (jedis != null) {
            jedis.hmset(key, map);
        }
    }

    /**
     * 往集合中压数据(多条数据用空格隔开)
     */
    public  void saveList(String key,String value){
        jedis = getJedis();
        if (jedis != null) {
            jedis.lpush(key, value);
        }
    }
    /**
     * 取出集合中的所有元素
     */
    public  List<String> getListAllValues(String key){
        jedis = getJedis();
        if (jedis != null) {
            List<String> lrange = jedis.lrange(key, 0, -1);
            return lrange;
        }
        return null;
    }
    /**
     * 获取两个集合的交集
     */
    public  Set<String> getSinter(String set1,String set2){
        jedis = getJedis();
        if (jedis != null ) {
            return jedis.sinter(set1,set2);
        }
        return null;
    }
    /**
     * 向集合中添加多条数据
     */
    public  void sadd(String key,String member[]){
        jedis = getJedis();
        if (jedis != null ) {
            jedis.sadd(key, member);
        }
    }
    /**
     * 向集合中添加单条数据
     */
    public  void sadd(String key,String member){
        jedis = getJedis();
        if (jedis != null ) {
            jedis.sadd(key, member);
        }
    }
    /**
     * 判断member是否为某个集合的元素
     */
    public Boolean sismember(String key,String member){
        jedis = getJedis();
        if (jedis != null ) {
            return jedis.sismember(key, member);
        }
        return null;
    }

    /**
     * 删除hash中的某个field
     */
    public void hdel(String key,String field){
        jedis = getJedis();
        if (jedis != null ) {
            jedis.hdel(key, field);
        }
    }

    /**
     * 删除set中的某个member
     */
    public void srem(String key,String member){
        jedis = getJedis();
        if (jedis != null ) {
            jedis.srem(key, member);
        }
    }

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
                if ("OK".equals(result)) {  // 返回成功，表示加锁成功
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

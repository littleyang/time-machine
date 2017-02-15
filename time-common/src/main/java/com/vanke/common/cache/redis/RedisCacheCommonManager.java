package com.vanke.common.cache.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

@SuppressWarnings("unchecked")
public abstract class RedisCacheCommonManager<T> {
	// default exipre time
    private final long DEFAULT_EXPIRE_TIME = 30*24*60*60;

    //default final time unit
    private final TimeUnit DEAULT_TIME_UNIT = TimeUnit.SECONDS;

    @Autowired
    protected RedisTemplate redisTemplate;

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 判断某个值是否存在
     * @param key
     * @return
     */
    public boolean existKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 增加某一个值
     * @param key
     * @param value
     */
    public void addValueBykey(String key,Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 将hash放入缓存中
     * @param hashKey
     * @param valueKey
     * @param value
     */
    public void addHashValueByKeyAndHashValueKey(Object hashKey, Object valueKey, Object value){
        redisTemplate.opsForHash().put(hashKey,valueKey,value);
    }

    /**
     * 根据某个key设置超时时间
     * @param key
     * @param value
     * @param timeout
     * @param unit
     */
    public void addValueBykeyAndExpire(String key,Object value,long timeout,TimeUnit unit){
        redisTemplate.opsForValue().set(key, value);
        if(timeout==0||timeout<0){
            timeout = DEFAULT_EXPIRE_TIME;
        }
        if(null==unit){
            unit = DEAULT_TIME_UNIT;
        }
        redisTemplate.expire(key, timeout, unit);
    }


    /**
     * 根据某个key设置超时时间
     * @param key
     * @param value
     * @param timeout
     * @param unit
     */
    public void addBoundValueBykeyAndExpire(String key,Object value,long timeout,TimeUnit unit){

        if(timeout==0||timeout<0){
            timeout = DEFAULT_EXPIRE_TIME;
        }
        if(null==unit){
            unit = DEAULT_TIME_UNIT;
        }

        BoundValueOperations<String, Object> boundOps = redisTemplate.boundValueOps(key);
        boundOps.set(value);
        boundOps.expire(timeout, unit);
    }

    /**
     *  pop 某一个key值
     */
    public Object getValueByKey(String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 读取hash里面的某个值
     */
    public Object getHashValue(Object hashKey, Object valueKey){
        return redisTemplate.opsForHash().get(hashKey,valueKey);
    }

    /** delete某个key值
     *
     */
    public void deleteValueBykey(String key){
        redisTemplate.delete(key);
    }

    /**
     * update 某个key值
     */
    public void updateValueBykey(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 增加一个list的值
     * @param key
     * @param values
     */
    public void addListValuesByKey(String key, List<Object> values){
        redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 返回某一个key的值
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> getListValuesByKey(String key,int start,int end){
        if(start<0||start==0){
            start = 0;
        }
        if(end<1){
            end = -1;
        }
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取一些列keys的值
     * @param keys
     * @return
     */
    public List<Object> getValuesByListsKeys(List<Object> keys){
        return redisTemplate.opsForValue().multiGet(keys);
    }


    /**
     *  在缓存中读取一串hash值
     */
    public  <HK> List<T> loadHashValuesByKeyAndValueKeys(final Object key, final List<HK> hashKeys){

        final List<HK> distinctHashKeys = hashKeys == null ? null : hashKeys.stream().distinct().collect(Collectors.toList());

        final List<T> entities = redisTemplate.opsForHash().multiGet(key,distinctHashKeys);

        return entities == null ? null : entities.stream().filter(x -> x != null).collect(Collectors.toList());
    }

    /**
     * 删除一些列的keys值
     * @param keys
     */
    public void deltedValuesByKeys(List<Object> keys){
        redisTemplate.delete(keys);
    }

}

package com.events.common.nosql.redis;

import com.events.common.Utility;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/7/14
 * Time: 1:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class RedisDAO {
    public static int put(String sResource, HashMap<String,String> hmRecords) {
        int iRowsAffected = 0;
        if(!Utility.isNullOrEmpty(sResource) && hmRecords!=null && !hmRecords.isEmpty() ) {
            Pool pool = Pool.getInstance(  sResource );
            if(pool!=null) {
                JedisPool jedisPool = pool.getJedisPool();
                if(jedisPool!=null ) {
                    Jedis jedis = jedisPool.getResource();
                    for(Map.Entry<String,String> mapRecords : hmRecords.entrySet() ) {
                        jedis.set(mapRecords.getKey(), mapRecords.getValue() );
                    }
                }
            }
        }
        return iRowsAffected;
    }
}

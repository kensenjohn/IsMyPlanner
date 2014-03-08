package com.events.common.nosql.redis;

import com.events.common.Constants;
import com.events.common.Utility;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        if(hmRecords!=null && !hmRecords.isEmpty() ) {
            Jedis jedis = getJedis(sResource);
            if(jedis!=null){
                for(Map.Entry<String,String> mapRecords : hmRecords.entrySet() ) {
                    String response = jedis.set(mapRecords.getKey(), mapRecords.getValue() );
                    if("OK".equalsIgnoreCase(response)) {
                        iRowsAffected = 1;
                    }
                }
            }
        }
        return iRowsAffected;
    }

    public static int putInHash(String sResource, String key, HashMap<String,String> hmRecords) {
        int iRowsAffected = 0;
        if(hmRecords!=null && !hmRecords.isEmpty() ) {
            Jedis jedis = getJedis(sResource);
            if(jedis!=null){
                String response = jedis.hmset(key , hmRecords );
                if("OK".equalsIgnoreCase(response)) {
                    iRowsAffected = 1;
                }
            }
        }
        return iRowsAffected;
    }
    public static HashMap<String,String> getFromHash(String sResource, String key ) {
        HashMap<String,String> hmResult = new HashMap<String, String>();
        if(!Utility.isNullOrEmpty(key) ) {
            Jedis jedis = getJedis(sResource);
            if(jedis!=null){
                hmResult = (HashMap<String,String>)jedis.hgetAll(key);
            }
        }
        return hmResult;
    }

    public static Long getId( String sResource, String key ) {
        Long lId = 0L;
        if(!Utility.isNullOrEmpty(key) ) {
            Jedis jedis = getJedis(sResource);
            if(jedis!=null){
                lId = jedis.incr( key );
            }
        }
        return lId;
    }

    public static Long pushId( String sResource, String key, String id ) {
        Long lId = 0L;
        if(!Utility.isNullOrEmpty(key) ) {
            Jedis jedis = getJedis(sResource);
            if(jedis!=null){
                lId = jedis.lpush(key,id);
            }
        }
        return lId;
    }

    public static ArrayList<String> getIdList( String sResource, String key){
        ArrayList<String> arrId = new ArrayList<String>();
        if(!Utility.isNullOrEmpty(key) ) {
            Jedis jedis = getJedis(sResource);
            if(jedis!=null){
                Long lNumOfId = getNumOfIdInList(jedis , key );
                if(lNumOfId>0){
                    arrId = (ArrayList<String>)jedis.lrange(key , 0, lNumOfId);
                }
            }
        }
        return arrId;
    }

    public static String popFromList( String sResource, String key){
        String listRecord = Constants.EMPTY;
        if(!Utility.isNullOrEmpty(key) ) {
            Jedis jedis = getJedis(sResource);
            if(jedis!=null){
                listRecord = jedis.lpop(key);
            }
        }
        return listRecord;
    }

    public static Long getNumOfIdInList(String sResource, String key) {
        Long lNumOfId = 0L;
        if(!Utility.isNullOrEmpty(key) ) {
            Jedis jedis = getJedis(sResource);
            lNumOfId = getNumOfIdInList(jedis , key );
        }
        return lNumOfId;
    }
    private static Long getNumOfIdInList(Jedis jedis, String key ) {
        Long lNumOfId = 0L;
        if(jedis!=null){
            lNumOfId = jedis.llen(key);
        }
        return lNumOfId;
    }

    private static Jedis getJedis(String sResource){
        Jedis jedis = null;
        if(!Utility.isNullOrEmpty(sResource) ) {
            Pool pool = Pool.getInstance(  sResource );
            if(pool!=null) {
                JedisPool jedisPool = pool.getJedisPool();
                if(jedisPool!=null ) {
                    jedis = jedisPool.getResource();
                }
            }
        }
        return jedis;
    }
}

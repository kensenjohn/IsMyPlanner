package com.events.common.nosql.redis;

import com.events.common.Constants;
import com.events.common.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
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
            returnJedis(jedis,sResource );
        }
        return iRowsAffected;
    }

    public static String get(String sResource, String key){
        String sValue = Constants.EMPTY;
        if(!Utility.isNullOrEmpty(key)) {
            Jedis jedis = getJedis(sResource);
            if(jedis!=null){
                sValue = jedis.get(key);
            }
            returnJedis(jedis,sResource );
        }
        return sValue;
    }

    public static String set(String sResource, String key , String value){
        String status = Constants.EMPTY;
        if(!Utility.isNullOrEmpty(key)) {
            Jedis jedis = getJedis(sResource);
            if(jedis!=null){
                status = jedis.set(key, value);
            }
            returnJedis(jedis,sResource );
        }
        return status;
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
            returnJedis(jedis,sResource );
        }
        return iRowsAffected;
    }
    public static HashMap<String,String> getAllFromHash(String sResource, String key) {
        HashMap<String,String> hmResult = new HashMap<String, String>();
        if(!Utility.isNullOrEmpty(key) ) {
            Jedis jedis = getJedis(sResource);
            if(jedis!=null){
                hmResult = (HashMap<String,String>)jedis.hgetAll(key);
            }
        }
        return hmResult;
    }
    public static String getValueFromHash(String sResource, String key , String field) {
        String hashValue = Constants.EMPTY;
        if(!Utility.isNullOrEmpty(key) && !Utility.isNullOrEmpty(field)) {
            Jedis jedis = getJedis(sResource);
            if(jedis!=null){
                Object objValue = jedis.hget(key, field);
                if(objValue!=null){
                    hashValue = (String)objValue;
                }
            }
            returnJedis(jedis,sResource );
        }
        return hashValue;
    }

    public static Long getId( String sResource, String key ) {
        Long lId = 0L;
        if(!Utility.isNullOrEmpty(key) ) {
            Jedis jedis = getJedis(sResource);
            if(jedis!=null){
                lId = jedis.incr( key );
            }
            returnJedis(jedis,sResource );
        }
        return lId;
    }

    public static Long incrementCounter( String sResource, String key, Long incrementBy ) {
        Long counter = 0L;
        if(!Utility.isNullOrEmpty(key) ) {
            Jedis jedis = getJedis(sResource);
            if(jedis!=null){
                counter = jedis.incrBy( key , incrementBy );
            }
            returnJedis(jedis,sResource );
        }
        return counter;
    }

    public static Long decrementCounter( String sResource, String key, Long decrementBy ) {
        Long counter = 0L;
        if(!Utility.isNullOrEmpty(key) ) {
            Jedis jedis = getJedis(sResource);
            if(jedis!=null){
                counter = jedis.decrBy(key , decrementBy );
            }
        }
        return counter;
    }

    public static Long pushId( String sResource, String key, String id ) {
        Long lId = 0L;
        if(!Utility.isNullOrEmpty(key) ) {
            Jedis jedis = getJedis(sResource);
            if(jedis!=null){
                lId = jedis.lpush(key,id);
            }
            returnJedis(jedis,sResource );
        }
        return lId;
    }

    public static ArrayList<String> getIdList( String sResource, String key){
        ArrayList<String> arrId = new ArrayList<String>();
        if(!Utility.isNullOrEmpty(key) ) {
            JedisPoolStructure jedisPoolStructure = getJedisStructure( sResource );
            if(jedisPoolStructure!=null  ){
                Jedis jedis = jedisPoolStructure.jedis;
                if(jedis!=null){
                    Long lNumOfId = getNumOfIdInList(jedis , key );
                    if(lNumOfId>0){
                        arrId = (ArrayList<String>)jedis.lrange(key , 0, lNumOfId);
                    }
                    returnJedisPoolStructure(jedisPoolStructure );
                }
            }
            /*Jedis jedis = getJedis(sResource);
            if(jedis!=null){
                Long lNumOfId = getNumOfIdInList(jedis , key );
                if(lNumOfId>0){
                    arrId = (ArrayList<String>)jedis.lrange(key , 0, lNumOfId);
                }
                returnJedis(jedis,sResource );
            }*/
        }
        return arrId;
    }

    public static String popFromList( String sResource, String key){
        String listRecord = Constants.EMPTY;
        if(!Utility.isNullOrEmpty(key) ) {
            Jedis jedis = getJedis(sResource);
            if(jedis!=null){
                listRecord = jedis.lpop(key);
                returnJedis(jedis,sResource );
            }
        }
        return listRecord;
    }

    public static Long getNumOfIdInList(String sResource, String key) {
        Long lNumOfId = 0L;
        if(!Utility.isNullOrEmpty(key) ) {
            Jedis jedis = getJedis(sResource);
            lNumOfId = getNumOfIdInList(jedis , key );
            returnJedis(jedis,sResource );
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

    private static JedisPoolStructure getJedisStructure(String sResource){
        //Jedis jedis = null;
        JedisPoolStructure jedisPoolStructure = new JedisPoolStructure();
        if(!Utility.isNullOrEmpty(sResource) ) {
            Pool pool = Pool.getInstance(  sResource );
            if(pool!=null) {
                JedisPool jedisPool = pool.getJedisPool();
                if(jedisPool!=null ) {
                    Jedis jedis = jedisPool.getResource();


                    jedisPoolStructure.jedisPool = jedisPool;
                    jedisPoolStructure.jedis = jedis;
                    jedisPoolStructure.resource = sResource;
                }


            }
        }
        return jedisPoolStructure;
    }

    private static void returnJedisPoolStructure(JedisPoolStructure jedisPoolStructure){
        if(jedisPoolStructure!=null && jedisPoolStructure.resource!=null) {
            Pool pool = Pool.getInstance(  jedisPoolStructure.resource );
            if(pool!=null) {
                pool.returnJedis(jedisPoolStructure.jedisPool, jedisPoolStructure.jedis);
            }
        }
    }

    private static void returnJedis(Jedis jedis, String sResource){
        if(!Utility.isNullOrEmpty(sResource) ) {
            Pool pool = Pool.getInstance(  sResource );
            if(pool!=null) {
                //pool.returnJedis(jedis);
            }
        }
    }

    private static class JedisPoolStructure{
        JedisPool jedisPool = null;
        Jedis jedis = null;
        String resource = null;
    }
}

package com.events.common.nosql.redis;

import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/7/14
 * Time: 2:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class Pool {
    private enum CONFIG_PROPS{HOST,PORT,PASSWORD,TIMEOUT};
    Configuration noSqlConfig = Configuration.getInstance(Constants.NO_SQL_PROP);
    private static Pool pool = null;
    private static HostAndPort hostAndPort = null ;
    private static HashMap<CONFIG_PROPS,String> hmConfig = new HashMap<CONFIG_PROPS,String>();

    private HashMap< String, HashMap<CONFIG_PROPS,String>> hmResourceConfig = new HashMap< String, HashMap<CONFIG_PROPS,String>>();
    private Pool(String sResourceName) {
        HashMap<CONFIG_PROPS,String> hmTmpConfig = new HashMap<CONFIG_PROPS,String>();
        hmTmpConfig.put( CONFIG_PROPS.HOST , noSqlConfig.get("redis." + ParseUtil.checkNull(sResourceName)+".host") );
        hmTmpConfig.put( CONFIG_PROPS.TIMEOUT , noSqlConfig.get("redis." + ParseUtil.checkNull(sResourceName)+".timeout", "1000" ) );
        hmTmpConfig.put( CONFIG_PROPS.PORT , noSqlConfig.get("redis.port" , "1000"  ) );
        hmTmpConfig.put( CONFIG_PROPS.PASSWORD , noSqlConfig.get("redis.password") );

        hmResourceConfig.put( sResourceName , hmTmpConfig );
    }


    public static Pool getInstance(String sResourceName ) {
        if (pool == null && !Utility.isNullOrEmpty(sResourceName)) {
            pool = new Pool(sResourceName);
            hmConfig = pool.hmResourceConfig.get(sResourceName);
            hostAndPort = new HostAndPort( hmConfig.get(CONFIG_PROPS.HOST) , ParseUtil.sToI( hmConfig.get(CONFIG_PROPS.PORT)) );
        }
        return pool;
    }

    public JedisPool getJedisPool(){
        JedisPool jedisPool = null;
        if(hostAndPort!=null && hmConfig!=null && !hmConfig.isEmpty()) {
            jedisPool = new JedisPool(new JedisPoolConfig(), hostAndPort.getHost(),
                    hostAndPort.getPort(), ParseUtil.sToI(hmConfig.get(CONFIG_PROPS.TIMEOUT)) , hmConfig.get( CONFIG_PROPS.PASSWORD ) );
        }
        return jedisPool;
    }

    public void returnJedis(JedisPool jedisPool, Jedis jedis){
        if( jedisPool!=null && jedis!=null ) {
            jedisPool.returnResource(  jedis );
        }
    }
}

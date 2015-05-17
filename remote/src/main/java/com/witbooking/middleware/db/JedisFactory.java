package com.witbooking.middleware.db;

import com.witbooking.middleware.resources.MiddlewareProperties;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * Created by mongoose on 11/23/14.
 */
public enum JedisFactory {

    INSTANCE;

    private JedisPool jedisPool;

    JedisFactory()
    {
        JedisPoolConfig config=new JedisPoolConfig();
        jedisPool = new JedisPool(config, MiddlewareProperties.REDIS_SERVER );
    }

    public static JedisFactory getInstance()
    {
        return INSTANCE;
    }

    public JedisPool getJedisPool()
    {
        return jedisPool;
    }


    public String generateKey(String ticker, String category){
        return ticker+":"+category;
    }
    public String generateIDKey(String ticker, String category){
        return "id"+":"+ticker+":"+category;
    }

    public String generateKey(String ticker, String category,String prefix,String suffix ){
        return prefix+":"+ticker+":"+category+":"+suffix;
    }
    public String generateIDKey(String ticker, String category,String prefix,String suffix ){
        return prefix+"id"+":"+ticker+":"+category+":"+suffix;
    }


}
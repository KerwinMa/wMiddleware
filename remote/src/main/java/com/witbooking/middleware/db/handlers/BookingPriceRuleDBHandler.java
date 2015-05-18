package com.witbooking.middleware.db.handlers;

import com.google.gson.Gson;
import com.witbooking.middleware.db.JedisFactory;
import com.witbooking.middleware.model.dynamicPriceVariation.BookingPriceRule;
import com.witbooking.middleware.utils.JsonUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mongoose on 11/25/14.
 */
public class BookingPriceRuleDBHandler {

    public static final String BOOKING_RULE_KEY="rules";

    public List<BookingPriceRule> getBookingPriceRules(String ticker){

        /*TODO: Support case when DB access is not possible*/
        JedisPool jedisPool=JedisFactory.INSTANCE.getJedisPool();
        Jedis jedis=jedisPool.getResource();

        /* HGETALL hotelTicker:rules */
        String rulesKey=JedisFactory.INSTANCE.generateKey(ticker,BOOKING_RULE_KEY);

        Map<String,String> bPriceRules= jedis.hgetAll(rulesKey);
        List<BookingPriceRule> bookingPriceRules=new ArrayList<BookingPriceRule>();

        Gson gson = JsonUtils.gson;

        for (Map.Entry<String, String> ruleEntry : bPriceRules.entrySet()) {
            String ruleID=ruleEntry.getKey();
            String jsonSerializedRule=ruleEntry.getValue();
            BookingPriceRule bookingPriceRule=gson.fromJson(jsonSerializedRule,BookingPriceRule.class);
            bookingPriceRules.add(bookingPriceRule);
        }

        jedisPool.returnResource(jedis);
        return bookingPriceRules;
    }


    public BookingPriceRule setBookingPriceRules(String ticker, BookingPriceRule bookingPriceRule){
        JedisPool jedisPool=JedisFactory.INSTANCE.getJedisPool();
        Jedis jedis=jedisPool.getResource();

        String rulesKey=JedisFactory.INSTANCE.generateKey(ticker,BOOKING_RULE_KEY);
        String rulesIDKey=JedisFactory.INSTANCE.generateIDKey(ticker, BOOKING_RULE_KEY);

        /*Todo: check for potential failure*/
        Gson gson = JsonUtils.gson;

        String ruleID= bookingPriceRule.getId()!=null ? String.valueOf(bookingPriceRule.getId()):String.valueOf(jedis.incr(rulesIDKey));
        bookingPriceRule.setId(Long.parseLong(ruleID));

        if(bookingPriceRule.getOrder()==0){
            bookingPriceRule.setOrder(Integer.parseInt(ruleID));
        }

        /* HSET hotelTicker:rules ruleTicker jsonSerializedRule */
        long status=jedis.hset(rulesKey,ruleID,gson.toJson(bookingPriceRule));

        bookingPriceRule.setId(Long.parseLong(ruleID));

        jedisPool.returnResource(jedis);
        return bookingPriceRule;
    }

    public long setBookingPriceRules(String ticker, List<BookingPriceRule> bookingPriceRules){
        JedisPool jedisPool=JedisFactory.INSTANCE.getJedisPool();
        Jedis jedis=jedisPool.getResource();

        String rulesKey=JedisFactory.INSTANCE.generateKey(ticker,BOOKING_RULE_KEY);
        String rulesIDKey=JedisFactory.INSTANCE.generateIDKey(ticker, BOOKING_RULE_KEY);

        Gson gson = JsonUtils.gson;
        for(BookingPriceRule bookingPriceRule : bookingPriceRules){
            if(bookingPriceRule.getId()==null){
                bookingPriceRule.setId(jedis.incr(rulesIDKey));
            }
            jedis.hset(rulesKey, String.valueOf(bookingPriceRule.getId()), gson.toJson(bookingPriceRule));
        }

        jedisPool.returnResource(jedis);
        return 0;
    }



    public long deleteBookingPriceRules(String ticker,  String ruleID){
        JedisPool jedisPool=JedisFactory.INSTANCE.getJedisPool();
        Jedis jedis=jedisPool.getResource();

        String rulesKey=JedisFactory.INSTANCE.generateKey(ticker,BOOKING_RULE_KEY);

        long status=jedis.hdel(rulesKey,ruleID);

        jedisPool.returnResource(jedis);
        return status;
    }


}

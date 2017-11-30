package com.pcdack.oscsmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 * Created by pcdack on 17-9-8.
 *
 */
public class TokenCache {
    private static Logger logger= LoggerFactory.getLogger(TokenCache.class);
    public static final String TOKEN_PREFIX = "token_";
    private static LoadingCache<String,String> localCache= CacheBuilder.newBuilder()
            .initialCapacity(1000)
            .maximumSize(10000)
            .expireAfterAccess(12, TimeUnit.DAYS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
//                    如果没有Key，直接返回null的Value
                    return "null";
                }
            });
    public static void setKey(String key,String value){
        localCache.put(key,value);
    }
    public static String getKey(String key){
        String value=null;
        try {
            value=localCache.get(key);
            if ("null".equals(value)){
                return null;
            }
            return value;
        } catch (ExecutionException e) {
            logger.error("local cache get error!",e);
        }
        return null;
    }


}

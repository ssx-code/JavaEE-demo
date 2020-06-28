package com.cy.java.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通过此对象异步记录查询操作的命中率
 * 1)选择日志库
 * 2)执行异步写操作。
 */
public class AsyncLoggingCache implements Cache {
    //日志门面应用
    private static Logger log = LoggerFactory.getLogger(LoggingCache.class);
    private Cache cache;
    /**
     * 表示请求次数
     */
    private int requests;
    /**
     * 命中次数(命中表示从缓存中取到数据了)
     */
    private int hits;

    public AsyncLoggingCache(Cache cache) {
        this.cache = cache;
    }

    @Override
    public void putObject(Object key, Object value) {
        cache.putObject(key, value);
    }

    @Override
    public Object getObject(Object key) {
        requests++;
        Object obj = cache.getObject(key);
        if (obj != null) hits++;
        //记录日志耗时
        //System.out.println("Cache hit Ratio:"+hits*1.0/requests);
        log.info("Cache hit Ratio:{}", hits * 1.0 / requests);
        return obj;
    }

    @Override

    public Object removeObject(Object key) {
        return cache.removeObject(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public int size() {
        return cache.size();
    }

    public static void main(String[] args) {
        Cache cache = new AsyncLoggingCache(new PerpetualCache());
        cache.putObject("A", 100);
        cache.putObject("B", 200);
        cache.putObject("C", 300);
        cache.putObject("D", 400);
        //System.out.println(cache);
        cache.getObject("E");
        cache.getObject("A");
        cache.getObject("B");
        cache.getObject("B");
    }

}

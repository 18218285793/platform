package cn.com.signheart.common.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.concurrent.locks.ReentrantLock;

public class EhcacheUtil {
    private CacheManager manager = CacheManager.create();
    private static EhcacheUtil ehCache;
    private static ReentrantLock lock = new ReentrantLock(true);

    private EhcacheUtil() {
    }

    public static EhcacheUtil getInstance() {
        try {
            lock.lock();
            if(ehCache == null) {
                ehCache = new EhcacheUtil();
            }
        } finally {
            lock.unlock();
        }

        return ehCache;
    }

    public Cache generateCache(String cacheName) {
        if(!this.manager.cacheExists(cacheName)) {
            this.manager.addCache(cacheName);
        }

        Cache cache = this.manager.getCache(cacheName);
        return cache;
    }

    public void put(String cacheName, String key, Object value) {
        if(!this.manager.cacheExists(cacheName)) {
            this.manager.addCache(cacheName);
        }

        Cache cache = this.manager.getCache(cacheName);
        Element element = new Element(key, value);
        cache.put(element);
    }

    public Object get(String cacheName, String key) {
        if(!this.manager.cacheExists(cacheName)) {
            return null;
        } else {
            Cache cache = this.manager.getCache(cacheName);
            Element element = cache.get(key);
            return element == null?null:element.getObjectValue();
        }
    }

    public Cache get(String cacheName) {
        return this.manager.getCache(cacheName);
    }

    public void remove(String cacheName, String key) {
        if(this.manager.cacheExists(cacheName)) {
            Cache cache = this.manager.getCache(cacheName);
            cache.remove(key);
        }
    }
}

package tech.coderonin.spring.memcached.cache;

import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static tech.coderonin.spring.redis.cache.Utility.isEmpty;

public class MemcachedCacheManager implements CacheManager {

    static final Logger log = LoggerFactory.getLogger(MemcachedCacheManager.class );

    int defaultExpiration = 5;

    Map<String, Cache> caches = new HashMap<>();

    @Autowired
    XMemcachedClientBuilder cacheClientBuilder;

    public MemcachedCacheManager(){
        System.out.println( "MemcachedCacheManager used" );
    }

    public int getDefaultExpiration() {
        return defaultExpiration;
    }

    public void setDefaultExpiration(int defaultExpiration) {
        this.defaultExpiration = defaultExpiration;
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = caches.get(name);
        if( cache == null ){
            cache = createCache(name);
            caches.put(name, cache);
        }
        return cache;
    }

    @Override
    public Collection<String> getCacheNames() {
        return caches.keySet();
    }

    protected Cache createCache( String name ){
        if( !isEmpty(name)){
            try {
                Cache newCache = new Memcached( name, defaultExpiration, cacheClientBuilder.build() );
                return newCache;
            } catch (IOException e) {
                log.error( "Unable to create cache " + e.getMessage() );
            }
        }
        return null;
    }
}

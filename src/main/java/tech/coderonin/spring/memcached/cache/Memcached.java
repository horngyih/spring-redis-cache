package tech.coderonin.spring.memcached.cache;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeoutException;

public class Memcached implements Cache {

    public static final Logger log = LoggerFactory.getLogger(Memcached.class );

    String name;
    int expiration;

    MemcachedClient cacheClient;

    public Memcached(String name, int expiration, MemcachedClient client ) throws IOException {
        this.name = name;
        this.expiration = expiration;
        this.cacheClient = client;
        System.out.println( "Using Memcached - " + name);
    }

    public String getName() {
        return this.name;
    }

    public int getExpiration() {
        return expiration;
    }

    public Object getNativeCache() {
        return this.cacheClient;
    }

    public ValueWrapper get(Object key) {
        Object value = null;
        try {
            value = cacheClient.get( key.toString() );
        } catch (TimeoutException | InterruptedException | MemcachedException ex ) {
            log.error( "Unable to retrieve from cache ", ex.getMessage() );
        }

        if(value != null){
            return new SimpleValueWrapper(value);
        } else{
            log.debug( "Log miss " + key.toString() );
        }
        return null;
    }

    public void put(Object key, Object value) {
        if( value != null && key != null){
            try {
                cacheClient.set( key.toString(), expiration, value );
            } catch (TimeoutException | InterruptedException | MemcachedException ex) {
                log.error( "Unable to put to cache", ex.getMessage() );
            }
        }
    }

    public void evict(Object key) {
        if( key != null ){
            try {
                cacheClient.delete( key.toString() );
            } catch (TimeoutException | InterruptedException | MemcachedException ex) {
                log.error( "Unable to delete from cache", ex.getMessage() );
            }
        }
    }

    public void clear() {
        try {
            cacheClient.flushAll();
        } catch (TimeoutException | InterruptedException | MemcachedException ex ) {
            log.error( "Unable to flush cache", ex.getMessage() );
        }
    }
}

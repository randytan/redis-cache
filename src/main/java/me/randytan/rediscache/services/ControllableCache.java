package me.randytan.rediscache.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;

@Service
@Slf4j
public class ControllableCache {

    private static final String CONTROLLED_PREFIX ="myControlledPrefix_";

    public static String getCacheKey(String relevant){
        return CONTROLLED_PREFIX.concat(relevant);
    }

    @Cacheable(cacheNames = "myControlledCache", key = "T(me.randytan.rediscache.services.ControllableCache).getCacheKey(#relevant)")
    public String getFromCache(String relevant){
        return null;
    }

    @CachePut(cacheNames = "myControlledCache", key = "T(me.randytan.rediscache.services.ControllableCache).getCacheKey(#relevant)")
    public String populateCache(String relevant, String unrelevantTrackingId){
        log.info("Showing unrelevant cache {}", unrelevantTrackingId);
        return "this is again";
    }

    @CacheEvict(cacheNames = "myControlledCache", key = "T(me.randytan.rediscache.services.ControllableCache).getCacheKey(#relevant)")
    public void removeFromCache(String relevant) {
    }

}

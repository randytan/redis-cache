package me.randytan.rediscache.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "cache")
@Data
public class CacheConfigurationProperties {

    @Value("${spring.redis.timeout}")
    private long timeoutSeconds;
    @Value("${spring.redis.port}")
    private int redisPort;
    @Value("${spring.redis.host}")
    private String redisHost; //set dns hostname in .local e.g. redis-prod01-cluster.local in properties.

    // Mapping of cacheNames to expiration-after-write timeout in seconds
    private Map<String, Long> cacheExpirations = new HashMap<>();

}

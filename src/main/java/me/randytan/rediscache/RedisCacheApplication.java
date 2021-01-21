package me.randytan.rediscache;

import me.randytan.rediscache.services.CacheService;
import me.randytan.rediscache.services.ControllableCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.UUID;


@EnableCaching
@Slf4j
@SpringBootApplication
public class RedisCacheApplication implements CommandLineRunner {

	@Autowired
	CacheService cacheService;

	@Autowired
	ControllableCache controllableCache;

	public static void main(String[] args) {
		SpringApplication.run(RedisCacheApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String firstString = cacheService.cacheThis("param1", UUID.randomUUID().toString());
		log.info("First: {}", firstString);
		String secondString = cacheService.cacheThis("param1", UUID.randomUUID().toString());
		log.info("Second: {}", secondString);
		String thirdString = cacheService.cacheThis("AnotherParam", UUID.randomUUID().toString());
		log.info("Third: {}", thirdString);
		String fourthString = cacheService.cacheThis("AnotherParam", UUID.randomUUID().toString());
		log.info("Fourth: {}", fourthString);

		// controlled cache
		log.info("Starting controlled cache: -----------");
		String controlledFirst = getFromControlledCache("first");
		log.info("Controlled First: {}", controlledFirst);
		String controlledSecond = getFromControlledCache("second");
		log.info("Controlled Second: {}", controlledSecond);

		getFromControlledCache("first");
		getFromControlledCache("second");
		getFromControlledCache("third");

		//remove cache
		//log.info("Clearing all cache entries:");
		//cacheService.forgetAboutThis();
		//controllableCache.removeFromCache();

	}

	private String getFromControlledCache(String param) {
		String fromCache = controllableCache.getFromCache(param);
		if (fromCache == null) {
			log.info("Oups - Cache was empty. Going to populate it");
			String newValue = controllableCache.populateCache(param, UUID.randomUUID().toString());
			log.info("Populated Cache with: {}", newValue);
			return newValue;
		}
		log.info("Returning from Cache: {}", fromCache);
		return fromCache;
	}
}

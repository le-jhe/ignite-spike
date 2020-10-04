package org.jhe.ignite.spike.cache;

import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.spring.SpringCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class CacheManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(CacheManager.class);
	private static final String PREFIX = "spike.";
	@Autowired
	private SpringCacheManager springCacheManager;

	@SuppressWarnings("unchecked")
	public <K, V> IgniteCache<K, V> getCache(String code, Class<K> key, Class<V> value) {
		LocalDateTime before = LocalDateTime.now();

		Cache cache = springCacheManager.getCache(PREFIX + code);

		if (cache == null) {
			throw new IllegalStateException("cannot retrieve cache for code=" + (PREFIX + code));
		}

		Object nativeCache = cache.getNativeCache();

		if (nativeCache instanceof IgniteCache) {
			LOGGER.debug("cache={} retrieved in {} ms.", cache.getName(),
					Duration.between(before, LocalDateTime.now()).getNano() / 1000000);
			return (IgniteCache<K, V>) nativeCache;
		}

		throw new IllegalStateException("the cache implementation is of unexpected class=" + nativeCache.getClass());
	}

}
package org.jhe.ignite.spike.service;

import java.util.HashMap;
import java.util.Map;

import javax.cache.Cache.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.ignite.IgniteCache;
import org.jhe.ignite.spike.cache.CacheManager;
import org.jhe.ignite.spike.model.SomeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SomeTypeService {
	public static final Logger LOGGER = LoggerFactory.getLogger(SomeTypeService.class);
	public static final String CACHE_REGION_NAME = "sometype";

	@Autowired
	private CacheManager cacheManager;
	private IgniteCache<String, SomeType> cache;

	public Map<String, SomeType> initCache() {
		Map<String, SomeType> someTypes = new HashMap<>();
		cache = cacheManager.getCache(CACHE_REGION_NAME, String.class, SomeType.class);
		if (cache.metrics().isEmpty()) {
			for (int i = 0; i < 100_000; i++) {
				String key = "515_K" + StringUtils.leftPad(String.valueOf(i), 5, "0");
				SomeType value = new SomeType(key, key);
				cache.putIfAbsentAsync(key, value);
				someTypes.put(key, value);
			}
		} else {
			LOGGER.warn("cache was already loaded");
			for (Entry<String, SomeType> entry : cache) {
				someTypes.put(entry.getKey(), entry.getValue());
			}

		}
		LOGGER.warn("filled cache with elements.#={}", someTypes.size());
		return someTypes;
	}

	public Map<String, SomeType> loadAll() {
		Map<String, SomeType> someTypes = new HashMap<>();
		cache = cacheManager.getCache(CACHE_REGION_NAME, String.class, SomeType.class);
		for (Entry<String, SomeType> entry : cache) {
			someTypes.put(entry.getKey(), entry.getValue());
		}

		return someTypes;
	}

	public SomeType load(String id) {
		SomeType someType = null;
		cache = cacheManager.getCache(CACHE_REGION_NAME, String.class, SomeType.class);
		someType = cache.get(id);
		if (someType == null) {
			LOGGER.warn("will fake a load... id={}", id);
			try {
				Thread.sleep(1_000);
			} catch (InterruptedException e) {
				LOGGER.error("unexpected", e);
			}
			someType = new SomeType(id, "I was born outside cache...");
			cache.put(id, someType);
		}
		return someType;
	}

	public SomeType put(SomeType someType) {
		cache = cacheManager.getCache(CACHE_REGION_NAME, String.class, SomeType.class);
		cache.put("515_" + someType.getId(), someType);
		return someType;
	}
}

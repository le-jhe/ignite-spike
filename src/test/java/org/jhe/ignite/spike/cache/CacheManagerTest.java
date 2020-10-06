package org.jhe.ignite.spike.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CachePeekMode;
import org.jhe.ignite.spike.TestContext;
import org.jhe.ignite.spike.model.SomeType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CacheManagerTest extends TestContext {
	@Autowired
	private CacheManager cacheManager;

	@Test
	public void testInject() {
		assertNotNull(cacheManager);
	}

	@Test
	public void testGetSomeCache() {
		IgniteCache<String, SomeType> cache = cacheManager.getCache("sometype", String.class, SomeType.class);
		assertNotNull(cache);
		int cacheSize = cache.size(CachePeekMode.OFFHEAP);
		cache.put("someKey", new SomeType());
		assertEquals(cacheSize + 1, cache.size(CachePeekMode.OFFHEAP));
		assertNotNull(cache.get("someKey"));
	}
}

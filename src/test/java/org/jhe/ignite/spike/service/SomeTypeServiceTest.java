package org.jhe.ignite.spike.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.jhe.ignite.spike.TestContext;
import org.jhe.ignite.spike.model.SomeType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class SomeTypeServiceTest extends TestContext {
	private static final Logger LOGGER = LoggerFactory.getLogger(SomeTypeServiceTest.class);
	@Autowired
	private SomeTypeService someTypeService;
	@Value("${sometype.cache.size:100000}")
	private int cacheSize;

	@Test
	public void test() {
		// Map<String,SomeType> someTypes = someTypeService.initCache();
		Map<String, SomeType> someTypes = someTypeService.loadAll();
		assertNotNull(someTypes);
		LOGGER.warn("sometype.cache.size={}", cacheSize);
		assertEquals(cacheSize, someTypes.size());
		assertTrue(someTypes.containsKey("515_K00011"));
	}

	@Test
	public void test2() {
		// Map<String, SomeType> someTypes = someTypeService.initCache();
		Map<String, SomeType> someTypes = someTypeService.loadAll();
		assertNotNull(someTypes);
		assertEquals(cacheSize, someTypes.size());
		assertTrue(someTypes.containsKey("515_K00011"));
		assertNotNull(someTypeService.load("515_K00011"));
		assertNotNull(someTypeService.load("515_Z00011"));
	}

}

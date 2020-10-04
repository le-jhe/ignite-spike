package org.jhe.ignite.spike.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.jhe.ignite.spike.TestContext;
import org.jhe.ignite.spike.model.SomeType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SomeTypeServiceTest extends TestContext {
	@Autowired
	private SomeTypeService someTypeService;

	@Test
	public void test() {
		Map<String,SomeType> someTypes = someTypeService.initCache();
		assertNotNull(someTypes);
		assertEquals(100_000, someTypes.size());
		assertTrue(someTypes.containsKey("515_K00011"));
	}

}

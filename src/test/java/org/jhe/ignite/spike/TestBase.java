package org.jhe.ignite.spike;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This test is here to show how properties files can be loaded from tests and how values can be retrieved.
 *
 * @author M999JVE
 */
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@TestPropertySource(locations = {"classpath:application.properties", "classpath:security.properties", "classpath:test.properties"})
public abstract class TestBase {
}

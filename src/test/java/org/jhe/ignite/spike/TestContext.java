package org.jhe.ignite.spike;

import java.time.Duration;

import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@EnableWebFlux
public abstract class TestContext extends TestBase {
	protected WebTestClient webTestClient;

	@Before
	public void setUp() {
		if (webTestClient == null) {
			webTestClient = WebTestClient
					.bindToServer()
					.baseUrl("http://127.0.0.1:24101")
					.responseTimeout(Duration.ofMillis(300000))
					.build();
		}
	}
}
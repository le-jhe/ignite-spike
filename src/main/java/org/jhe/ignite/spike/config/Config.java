package org.jhe.ignite.spike.config;

import org.jhe.ignite.spike.service.SomeTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@Configuration
public class Config {
	private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
	@Autowired
	private SomeTypeService someTypeService;
//	@Bean
//	public ServletContextInitializer servletContextInitializer() {
//		return servletContext -> {
//			servletContext.getSessionCookieConfig().setName(COOKIE_NAME);
//			servletContext.getSessionCookieConfig().setPath(COOKIE_PATH);
//			servletContext.getSessionCookieConfig().setDomain("");
//		};
//	}

	@Bean
	public ObjectMapper getObjectMapper() {
		LOGGER.info("dates should not be treated as timestamps");
		return new ObjectMapper()
				.registerModule(new ParameterNamesModule())
				.registerModule(new Jdk8Module())
				.registerModule(new JavaTimeModule())
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

//	@Bean
//	public VersionHolder getVersionHolder(ApplicationContext context) {
//		return new VersionHolder(context);
//	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		// java.security.Security.addProvider(new
		// org.bouncycastle.jce.provider.BouncyCastleProvider());

		try {
			LOGGER.warn("I am preloading data in cache, so that is ready when first call arrives");
			someTypeService.initCache();
			LOGGER.warn("did something after startup..");
		} catch (Exception e) {
			LOGGER.error("cannot load all someTypes at startup !", e);
		} finally {
		}
	}
}

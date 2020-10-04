package org.jhe.ignite.spike.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Configuration
/**
 * It seems that having multiple classes that 'extends
 * WebMvcConfigurationSupport' does not work; only the first one is honored
 * 
 * @author jhe
 *
 */
public class InterceptorConfig /* extends WebMvcConfigurationSupport */ {
	private static final Logger LOGGER = LoggerFactory.getLogger(InterceptorConfig.class);

	/*
	 * @Override public void addInterceptors(InterceptorRegistry registry) {
	 * LOGGER.warn("adding a request interceptor.."); registry.addInterceptor(new
	 * IgniteSpikeInterceptor()).addPathPatterns("/**");
	 * LOGGER.warn("interceptors={}", registry);
	 *
	 * }
	 */
}

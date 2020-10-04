package org.jhe.ignite.spike.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jhe.ignite.spike.interceptor.IgniteSpikeInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("!test")
public class SwaggerConfig extends WebMvcConfigurationSupport {
	private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfig.class);

	public static final String BEARER = "Bearer";
	public static final String HEADER = "header";
	public static final String MODEL_REFERENCE_TYPE = "string";

	@Bean
	public Docket api() {
		// Adding Header
		ParameterBuilder paramBuilder = new ParameterBuilder();
		List<Parameter> params = new ArrayList<>();

		params.clear();
		// https://github.com/springfox/springfox/issues/1812
		paramBuilder.name(HttpHeaders.AUTHORIZATION).modelRef(new ModelRef(MODEL_REFERENCE_TYPE)).parameterType(HEADER)
				.required(false).build();

		params.add(paramBuilder.build());

		return new Docket(DocumentationType.SWAGGER_2).globalOperationParameters(params).select()
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
				.securitySchemes(Collections.singletonList(apiKey()));
	}

	/**
	 * https://github.com/springfox/springfox/issues/1804
	 * https://github.com/springfox/springfox/issues/1812 Trying to add a place to
	 * enter the jwt token value
	 *
	 * @return an apiKey
	 */
	private ApiKey apiKey() {
		return new ApiKey(HttpHeaders.AUTHORIZATION, BEARER, HEADER);
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LOGGER.warn("adding a request interceptor..");
		registry.addInterceptor(new IgniteSpikeInterceptor()).addPathPatterns("/**");
		LOGGER.warn("interceptors={}", registry);

	}
}
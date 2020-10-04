package org.jhe.ignite.spike.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/dummy")
@Api(tags = "Dummy")
public class DummyController {
	private static Logger LOGGER = LoggerFactory.getLogger(DummyController.class);
	@GetMapping("/hello")
	public String hello() {
		LOGGER.warn("just for testing");
		return "world";
	}

}

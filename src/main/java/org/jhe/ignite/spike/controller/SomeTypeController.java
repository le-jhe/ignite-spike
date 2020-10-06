package org.jhe.ignite.spike.controller;

import java.util.Map;

import org.jhe.ignite.spike.model.SomeType;
import org.jhe.ignite.spike.service.SomeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/sometypes")
@Api(tags = "SomeType")
public class SomeTypeController {
	@Autowired
	private SomeTypeService someTypeService;

	@GetMapping
	public Map<String, SomeType> loadAll() {
		return someTypeService.loadAll();
	}

	@GetMapping("/{id}")
	public SomeType load(@PathVariable String id) {
		return someTypeService.load(id);
	}
	@PostMapping
	public SomeType create(@RequestBody SomeType someType) {
		return someTypeService.put(someType);
	}
}

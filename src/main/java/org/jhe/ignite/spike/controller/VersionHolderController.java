package org.jhe.ignite.spike.controller;

import org.jhe.ignite.spike.utils.VersionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/version")
@Api(tags = "VersionHolder")
public class VersionHolderController {

	@Autowired
	private VersionHolder versionHolder;

	@GetMapping
	public String getVersion() {
		return versionHolder.getVersion();
	}
}

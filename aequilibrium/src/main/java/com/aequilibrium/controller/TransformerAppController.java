package com.aequilibrium.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransformerAppController {

	@GetMapping("/")
	public String ping() {
		return "You have reached the Transformers' application";
	}

}
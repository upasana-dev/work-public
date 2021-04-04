package com.aequilibrium.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransformerController {

	@RequestMapping("/")
	public String ping() {
		return "You have reached the Transformers' application";
	}
}

package com.aequilibrium.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aequilibrium.dto.TransformerDataDto;
import com.aequilibrium.dto.TransformerUpdateDto;
import com.aequilibrium.service.TransformerService;
import com.sun.jdi.request.InvalidRequestStateException;

@RestController
public class TransformerController {
	
	@Autowired
	private TransformerService service;

	@GetMapping("/")
	public String ping() {
		return "You have reached the Transformers' application";
	}
	
	@GetMapping("/list")
	public List<TransformerDataDto> listTransformers() {
		return service.listTransformers();
	}
	
	@PostMapping(path="/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	public TransformerDataDto addTransformer(@RequestBody TransformerUpdateDto transformerToCreate) {
		return service.createTransformer(transformerToCreate);
	}
}

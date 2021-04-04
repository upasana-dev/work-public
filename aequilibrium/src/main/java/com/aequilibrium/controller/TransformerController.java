package com.aequilibrium.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aequilibrium.dto.TransformerDataDto;
import com.aequilibrium.service.TransformerService;

@RestController
public class TransformerController {
	
	@Autowired
	private TransformerService service;

	@RequestMapping("/")
	public String ping() {
		return "You have reached the Transformers' application";
	}
	
	@RequestMapping("/list")
	public List<TransformerDataDto> listTransformers() {
		return service.listTransformers();
	}
}

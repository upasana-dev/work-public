package com.aequilibrium.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aequilibrium.dto.TransformerDataDto;
import com.aequilibrium.dto.TransformerUpdateDto;
import com.aequilibrium.dto.TransformerWarResultDto;
import com.aequilibrium.service.TransformerBattleService;
import com.aequilibrium.service.TransformerService;

@RestController
@RequestMapping("/transformers")
public class TransformerController {
	
	@Autowired
	private TransformerService service;
	
	@Autowired
	private TransformerBattleService battleService;
	
	@GetMapping()
	public List<TransformerDataDto> listTransformers() {
		return service.listTransformers();
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public TransformerDataDto addTransformer(@RequestBody TransformerUpdateDto transformerCreationData) {
		return service.createOrUpdateTransformer(transformerCreationData);
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public TransformerDataDto updateTransformer(@RequestBody TransformerUpdateDto transformerModificationData) {
		return service.createOrUpdateTransformer(transformerModificationData);
	}
	
	@GetMapping(path="/war", consumes = MediaType.APPLICATION_JSON_VALUE)
	public TransformerWarResultDto wageWar(@RequestBody List<String> transformerNames) {
		
		if(CollectionUtils.isEmpty(transformerNames)) {
			throw new RuntimeException("Invalid request, no transformers specified");
		}
		
		return battleService.computeWar(transformerNames);
	}
}

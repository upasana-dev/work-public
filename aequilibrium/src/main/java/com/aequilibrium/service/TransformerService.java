package com.aequilibrium.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aequilibrium.dto.TransformerDataDto;
import com.aequilibrium.mapper.TransformerDtoMapper;
import com.aequilibrium.model.Transformer;
import com.aequilibrium.repository.TransformerRepository;

@Service
public class TransformerService {
	
	@Autowired
	private TransformerRepository transformerRepository;
	
	@Autowired
	private TransformerDtoMapper creationMapper;
	
	
	public List<TransformerDataDto> listTransformers(){
		Iterable<Transformer> transformerIterable = transformerRepository.findAll();
		
		List<Transformer> result = new ArrayList<>();
		transformerIterable.iterator().forEachRemaining(result::add);
		
		return creationMapper.domainToDtoList(result);
	}
	
	
//	public TransformerDataDto createTransformer(TransformerDataDto transformerToCreate) {
//		
//		Transformer newTransformer = creationMapper.mapToModel(transformerToCreate);
//		
//		Transformer savedTransformer = transformerRepository.save(newTransformer);
//		
//		return creationMapper.domainToDto(savedTransformer);
//		
//	}

}

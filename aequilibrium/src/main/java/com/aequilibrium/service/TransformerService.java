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
	private TransformerDtoMapper dataDtoMapper;
	
	
	public List<TransformerDataDto> listTransformers(){
		Iterable<Transformer> transformerIterable = transformerRepository.findAll();
		
		List<Transformer> result = new ArrayList<>();
		transformerIterable.iterator().forEachRemaining(result::add);
		
		return dataDtoMapper.domainToDtoList(result);
	}
	
	
	public TransformerDataDto createTransformer(TransformerDataDto transformerToCreate) {
		
		Transformer newTransformer = dataDtoMapper.mapToModel(transformerToCreate);
		
		System.out.println(newTransformer);
		
		//The skill is a composite value, computed based on the other attributes of the transformer
		newTransformer.setSkill(computeSkill(transformerToCreate.getStrength(), transformerToCreate.getEndurance(), transformerToCreate.getFirepower(), transformerToCreate.getIntelligence(), transformerToCreate.getSpeed()));
		
		Transformer savedTransformer = transformerRepository.save(newTransformer);
		
		return dataDtoMapper.domainToDto(savedTransformer);
		
	}
	
	int computeSkill(int strength, int endurance, int firepower, int intelligence, int speed) {
		return strength+ endurance + firepower + intelligence + speed;
	}

}

package com.aequilibrium.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.aequilibrium.dto.TransformerDataDto;
import com.aequilibrium.dto.TransformerUpdateDto;
import com.aequilibrium.mapper.TransformerDtoMapper;
import com.aequilibrium.mapper.UpdateTransformerDtoMapper;
import com.aequilibrium.model.Transformer;
import com.aequilibrium.repository.TransformerRepository;

@Service
public class TransformerService {

	Logger logger = LoggerFactory.getLogger(TransformerService.class);

	@Autowired
	private TransformerRepository transformerRepository;

	@Autowired
	private TransformerDtoMapper dataDtoMapper;

	@Autowired
	private UpdateTransformerDtoMapper updateDtoMapper;

	public List<TransformerDataDto> listTransformers() {
		Iterable<Transformer> transformerIterable = transformerRepository.findAll();

		List<Transformer> result = new ArrayList<>();
		transformerIterable.iterator().forEachRemaining(result::add);

		return dataDtoMapper.domainToDtoList(result);
	}

	public TransformerDataDto createTransformer(TransformerUpdateDto transformerToCreate) {

		if (StringUtils.hasText(transformerToCreate.getName())) {
			throw new RuntimeException("Invalid transformer, no name specified, creation aborted");
		}

		if (transformerToCreate.getTransformerType() == null) {
			throw new RuntimeException("Invalid transformer, no type specified, creation aborted");
		}

		Transformer newTransformer = updateDtoMapper.mapToModel(transformerToCreate);

		System.out.println(newTransformer);

		// The skill is a composite value, computed based on the other attributes of the
		// transformer
		newTransformer.setSkill(computeSkill(transformerToCreate.getStrength(), transformerToCreate.getEndurance(),
				transformerToCreate.getFirepower(), transformerToCreate.getIntelligence(),
				transformerToCreate.getSpeed()));

		Transformer savedTransformer = transformerRepository.save(newTransformer);

		return dataDtoMapper.domainToDto(savedTransformer);

	}

	

	int computeSkill(int strength, int endurance, int firepower, int intelligence, int speed) {
		return strength + endurance + firepower + intelligence + speed;
	}

}

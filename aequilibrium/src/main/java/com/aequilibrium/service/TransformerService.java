package com.aequilibrium.service;

import java.util.ArrayList;
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
		if(transformerToCreate==null) {
			throw new RuntimeException("Data cannot be null!");
		}

		if (!StringUtils.hasText(transformerToCreate.getName())) {
			throw new RuntimeException("Invalid transformer, no name specified, creation aborted");
		}

		if (transformerToCreate.getTransformerType() == null) {
			throw new RuntimeException("Invalid transformer, no type specified, creation aborted");
		}

		Transformer savedTransformer = transformerRepository.save(updateDtoMapper.mapToModel(transformerToCreate));

		return dataDtoMapper.domainToDto(savedTransformer);

	}

}

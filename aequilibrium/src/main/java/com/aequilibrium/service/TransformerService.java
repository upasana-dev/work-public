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

		return dataDtoMapper.modelToDtoList(result);
	}

	public TransformerDataDto createOrUpdateTransformer(TransformerUpdateDto transformerModificationData) {
		if(transformerModificationData==null) {
			throw new RuntimeException("Update data cannot be null!");
		}

		if (!StringUtils.hasText(transformerModificationData.getName())) {
			throw new RuntimeException("Invalid transformer, no name specified, update aborted");
		}

		if (transformerModificationData.getTransformerType() == null) {
			throw new RuntimeException("Invalid transformer, no type specified, update aborted");
		}
		
		//Check if a transformer with the provided name already exists in the DB
		Transformer dbTransformer = transformerRepository.findByName(transformerModificationData.getName());
		
		if(dbTransformer==null) {
			//Create new Transformer object
			dbTransformer = updateDtoMapper.mapToModel(transformerModificationData);
		} else {
			// Transformer matching the name provided, already exists in the DB, will update this entry instead 
			updateDtoMapper.updateModel(dbTransformer, transformerModificationData);
		}

		return dataDtoMapper.modelToDto(transformerRepository.save(dbTransformer));

	}

}

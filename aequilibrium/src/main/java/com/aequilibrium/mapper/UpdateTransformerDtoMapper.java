package com.aequilibrium.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.aequilibrium.dto.TransformerUpdateDto;
import com.aequilibrium.model.Transformer;

/**
 * Maps data between {@link TransformerUpdateDto} and its model
 * {@link Transformer}
 * 
 * @author Upasana
 *
 */
@Mapper(componentModel = "spring")
public interface UpdateTransformerDtoMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "name", ignore = true)
	@Mapping(source = "updateDto", target = "overallRating", qualifiedByName = "computeRating")
	public void updateModel(@MappingTarget Transformer transformerFromDb, TransformerUpdateDto updateDto);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "dto", target = "overallRating", qualifiedByName = "computeRating")
	public Transformer mapToModel(TransformerUpdateDto dto);
	
	@Named("computeRating")
	default int computeOverallRating(TransformerUpdateDto dto) {
		return dto.getStrength()+ dto.getEndurance()+
				dto.getFirepower()+ dto.getIntelligence()+
				dto.getSpeed();
	}

}

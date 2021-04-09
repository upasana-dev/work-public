package com.aequilibrium.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
	public Transformer mapToModel(TransformerUpdateDto dto);

	public TransformerUpdateDto domainToDto(Transformer model);

}

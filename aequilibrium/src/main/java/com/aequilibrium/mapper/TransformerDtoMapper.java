package com.aequilibrium.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aequilibrium.dto.TransformerDataDto;
import com.aequilibrium.model.Transformer;

/**
 * Maps data between {@link TransformerDataDto} and its model {@link Transformer}
 * 
 * @author Upasana
 *
 */
@Mapper(componentModel="spring")
public interface TransformerDtoMapper {
	
	@Mapping(target = "id", ignore = true)
	public Transformer mapToModel(TransformerDataDto dto);
	
	public TransformerDataDto domainToDto(Transformer model);
	
	public List<TransformerDataDto> domainToDtoList(List<Transformer> modelObjects);

}

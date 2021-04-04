package com.aequilibrium.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.aequilibrium.dto.TransformerDataDto;
import com.aequilibrium.model.Transformer;

@Mapper(componentModel="spring")
public interface TransformerDtoMapper {
	
	public Transformer mapToModel(TransformerDataDto dto);
	
	public TransformerDataDto domainToDto(Transformer model);
	
	public List<TransformerDataDto> domainToDtoList(List<Transformer> modelObjects);

}

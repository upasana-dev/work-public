package com.aequilibrium.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.aequilibrium.dto.TransformerUpdateDto;
import com.aequilibrium.model.Transformer;
import com.aequilibrium.model.constants.TransformerType;

public class UpdateTransformerDtoMapperTest {
	
	UpdateTransformerDtoMapper mapper = Mappers.getMapper(UpdateTransformerDtoMapper.class );
	
	@Test
	public void testComputeOverallRating_nonZeroValues() {
		TransformerUpdateDto updateDto = new TransformerUpdateDto();
		updateDto.setEndurance(8);
		updateDto.setSpeed(2);
		updateDto.setStrength(3);
		updateDto.setFirepower(5);
		updateDto.setIntelligence(7);
		
		assertEquals(25, mapper.computeOverallRating(updateDto));
	}
	
	@Test
	public void testComputeOverallRating_someZeroValues() {
		TransformerUpdateDto updateDto = new TransformerUpdateDto();
		updateDto.setEndurance(0);
		updateDto.setSpeed(2);
		updateDto.setStrength(3);
		updateDto.setFirepower(0);
		updateDto.setIntelligence(7);
		
		assertEquals(12, mapper.computeOverallRating(updateDto));
	}
	
	@Test
	public void testComputeOverallRating_allZeroValues() {
		TransformerUpdateDto updateDto = new TransformerUpdateDto();
		updateDto.setEndurance(0);
		updateDto.setSpeed(0);
		updateDto.setStrength(0);
		updateDto.setFirepower(0);
		updateDto.setIntelligence(0);
		
		assertEquals(0, mapper.computeOverallRating(updateDto));
	}
	
	@Test
	public void testMapToModel_withData() {
		TransformerUpdateDto updateDto = new TransformerUpdateDto();
		updateDto.setCourage(6);
		updateDto.setEndurance(8);
		updateDto.setSpeed(2);
		updateDto.setStrength(3);
		updateDto.setFirepower(5);
		updateDto.setIntelligence(7);
		updateDto.setRank(9);
		updateDto.setSkill(5);
		updateDto.setName("Auto 1");
		updateDto.setTransformerType(TransformerType.D);
		
		Transformer model = mapper.mapToModel(updateDto);
		
		assertNotNull(model);
		assertEquals(updateDto.getCourage(), model.getCourage());
		assertEquals(updateDto.getEndurance(), model.getEndurance());
		assertEquals(updateDto.getSpeed(), model.getSpeed());
		assertEquals(updateDto.getFirepower(), model.getFirepower());
		assertEquals(updateDto.getIntelligence(), model.getIntelligence());
		assertEquals(updateDto.getName(), model.getName());
		assertEquals(updateDto.getRank(), model.getRank());
		assertEquals(updateDto.getStrength(), model.getStrength());
		assertEquals(updateDto.getSkill(), model.getSkill());
		assertEquals(updateDto.getTransformerType(), model.getTransformerType());
		assertEquals(25, model.getOverallRating());
	}
	
	@Test
	public void testMapToModel_nullDto() {
		
		assertNull(mapper.mapToModel(null));
	}

}

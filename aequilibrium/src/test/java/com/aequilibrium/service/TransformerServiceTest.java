package com.aequilibrium.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TransformerServiceTest {
	
	private TransformerService service = new TransformerService();
	
	@Test
	public void testComputeSkill_nonZeroValues() {
		int strength = 3;
		int endurance = 8;
		int speed = 2;
		int firepower = 5;
		int intelligence = 7;
		
		assertEquals(25, service.computeSkill(strength, endurance, firepower, intelligence, speed));
	}
	
	@Test
	public void testComputeSkill_someZeroValues() {
		int strength = 3;
		int endurance = 0;
		int speed = 2;
		int firepower = 0;
		int intelligence = 7;
		
		assertEquals(12, service.computeSkill(strength, endurance, firepower, intelligence, speed));
	}
	
	@Test
	public void testComputeSkill_allZeroValues() {
		int strength = 0;
		int endurance = 0;
		int speed = 0;
		int firepower = 0;
		int intelligence = 0;
		
		assertEquals(0, service.computeSkill(strength, endurance, firepower, intelligence, speed));
	}

}

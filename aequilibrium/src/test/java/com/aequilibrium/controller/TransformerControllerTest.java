package com.aequilibrium.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TransformerControllerTest {
	
	private TransformerController transformerController = new TransformerController();
	
	@Test
	public void testPing() {
		
		assertEquals("You have reached the Transformers' application", transformerController.ping());
		
	}

}

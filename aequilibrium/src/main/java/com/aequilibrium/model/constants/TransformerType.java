package com.aequilibrium.model.constants;

/**
 * Depicts the different types of Transformers. So far, this includes:-
 * <ul>
 * <li> A - Autobots
 * <li> D - Decepticons
 * </ul>
 * 
 * @author Upasana
 *
 */
public enum TransformerType {
	A("Autobot"),
	D("Decepticon");
	
	private TransformerType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	private String label;
}

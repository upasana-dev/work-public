package com.aequilibrium.dto;

import java.util.ArrayList;
import java.util.List;

public class TransformerWarTeamResultDto {
	
	//Name of the team
	private String name;
	
	//Names of the survivors from the team
	private List<String> survivors = new ArrayList<String>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getSurvivors() {
		return survivors;
	}
	public void setSurvivors(List<String> survivors) {
		this.survivors = survivors;
	}
	
	public TransformerWarTeamResultDto(String name, List<String> survivors) {
		super();
		this.name = name;
		this.survivors = survivors;
	}
	
	public TransformerWarTeamResultDto() {
		super();
	}
}

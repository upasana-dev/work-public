package com.aequilibrium.dto;

import java.util.List;

/**
 * Structure encapsulating the results of a Transformers war, including the
 * battles fought, the winner of the war and the survivors from each battle
 * 
 * @author Upasana
 *
 */
public class TransformerWarResultDto {

	// Name of the winning team
	private String winningTeam;

	// Number of battles fought in the war
	private int numberOfBattles;

	// Provides the results of the teams involved
	private List<TransformerWarTeamResultDto> teams;

	public String getWinningTeam() {
		return winningTeam;
	}

	public void setWinningTeam(String winningTeam) {
		this.winningTeam = winningTeam;
	}

	public int getNumberOfBattles() {
		return numberOfBattles;
	}

	public void setNumberOfBattles(int numberOfBattles) {
		this.numberOfBattles = numberOfBattles;
	}

	public List<TransformerWarTeamResultDto> getTeams() {
		return teams;
	}

	public void setTeams(List<TransformerWarTeamResultDto> teams) {
		this.teams = teams;
	}

}

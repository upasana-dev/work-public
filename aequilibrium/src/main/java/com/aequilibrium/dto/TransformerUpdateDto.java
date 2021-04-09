package com.aequilibrium.dto;

import com.aequilibrium.model.constants.TransformerType;

/**
 * The purpose of this structure is to encapsulate data required to update
 * or create new Transformers and will not include computed fields like 'skill'
 * 
 * @author Upasana
 *
 */
public class TransformerUpdateDto {

	private String name;

	private int strength;
	private int intelligence;
	private int speed;
	private int endurance;
	private int rank;
	private int courage;
	private int firepower;
	private TransformerType transformerType;

	@Override
	public String toString() {
		return "TransformerUpdateDto [name=" + name + ", strength=" + strength + ", intelligence=" + intelligence
				+ ", speed=" + speed + ", endurance=" + endurance + ", rank=" + rank + ", courage=" + courage
				+ ", firepower=" + firepower + ", transformerType=" + transformerType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + courage;
		result = prime * result + endurance;
		result = prime * result + firepower;
		result = prime * result + intelligence;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + rank;
		result = prime * result + speed;
		result = prime * result + strength;
		result = prime * result + ((transformerType == null) ? 0 : transformerType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransformerUpdateDto other = (TransformerUpdateDto) obj;
		if (courage != other.courage)
			return false;
		if (endurance != other.endurance)
			return false;
		if (firepower != other.firepower)
			return false;
		if (intelligence != other.intelligence)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (rank != other.rank)
			return false;
		if (speed != other.speed)
			return false;
		if (strength != other.strength)
			return false;
		if (transformerType != other.transformerType)
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getEndurance() {
		return endurance;
	}

	public void setEndurance(int endurance) {
		this.endurance = endurance;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getCourage() {
		return courage;
	}

	public void setCourage(int courage) {
		this.courage = courage;
	}

	public int getFirepower() {
		return firepower;
	}

	public void setFirepower(int firepower) {
		this.firepower = firepower;
	}

	public TransformerType getTransformerType() {
		return transformerType;
	}

	public void setTransformerType(TransformerType transformerType) {
		this.transformerType = transformerType;
	}

}

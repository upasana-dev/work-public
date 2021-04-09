package com.aequilibrium.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.aequilibrium.model.constants.TransformerType;

@Entity
public class Transformer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	

	private int strength;
	private int intelligence;
	private int speed;
	private int endurance;
	private int rank;
	private int courage;
	private int firepower;
	
	private int skill;
	
	@Enumerated(EnumType.STRING)
	private TransformerType transformerType;

	public Transformer() {
		super();
	}

	public Transformer(Long id, int strength, int intelligence, int speed, int endurance, int rank, int courage,
			int firepower, int skill, TransformerType type) {
		super();
		this.id = id;
		this.strength = strength;
		this.intelligence = intelligence;
		this.speed = speed;
		this.endurance = endurance;
		this.rank = rank;
		this.courage = courage;
		this.firepower = firepower;
		this.skill = skill;
		this.transformerType = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + courage;
		result = prime * result + endurance;
		result = prime * result + firepower;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + intelligence;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + rank;
		result = prime * result + skill;
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
		Transformer other = (Transformer) obj;
		if (courage != other.courage)
			return false;
		if (endurance != other.endurance)
			return false;
		if (firepower != other.firepower)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		if (skill != other.skill)
			return false;
		if (speed != other.speed)
			return false;
		if (strength != other.strength)
			return false;
		if (transformerType != other.transformerType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transformer [id=" + id + ", name=" + name + ", strength=" + strength + ", intelligence=" + intelligence
				+ ", speed=" + speed + ", endurance=" + endurance + ", rank=" + rank + ", courage=" + courage
				+ ", firepower=" + firepower + ", skill=" + skill + ", transformerType=" + transformerType + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getSkill() {
		return skill;
	}

	public void setSkill(int skill) {
		this.skill = skill;
	}

	public TransformerType getTransformerType() {
		return transformerType;
	}

	public void setTransformerType(TransformerType transformerType) {
		this.transformerType = transformerType;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}

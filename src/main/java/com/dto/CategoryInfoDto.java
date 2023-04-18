package com.dto;

public class CategoryInfoDto {

	private Integer id;

	private String name;

	private String unit;

	private String observation;

	SocietyInfoDto society;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public SocietyInfoDto getSociety() {
		return society;
	}

	public void setSociety(SocietyInfoDto society) {
		this.society = society;
	}

}

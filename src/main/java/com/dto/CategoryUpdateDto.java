package com.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoryUpdateDto {

	@NotNull(message = "L'id est nul !")
	private Integer id;

	@NotNull(message = "Le nom est nul !")
	@Size(min = 3, max = 50, message = "Le nom doit être comprise entre 3 et 50 caractères !")
	private String name;

	@Size(max = 5, message = "L'unité dépasse 5 caractères !")
	private String unit;

	@Size(max = 100, message = "L'observation dépasse 100 caractères !")
	private String observation;

	@NotNull(message = "La societé est nul !")
	private Integer society;

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

	public Integer getSociety() {
		return society;
	}

	public void setSociety(Integer society) {
		this.society = society;
	}

}

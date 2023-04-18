package com.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProfessionAddDto {

	@NotNull(message = "Le nom de profession est nul !")
	@Size(min = 3, max = 50, message = "Le nom de profession doit être comprise entre 3 et 50 caractères !")
	private String name;

	@Size(max = 5, message = "L'abréviation dépasse 5 caractères !")
	private String abbreviation;

	@Size(max = 100, message = "L'observation dépasse 100 caractères !")
	private String observation;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

}

package com.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TypeProjectUpdateDto {

	@NotNull(message = "L'id est nul !")
	private Integer id;

	@NotNull(message = "Le nom du type de projet est nul !")
	@Size(min = 3, max = 50, message = "Le nom du type de projet doit être comprise entre 3 et 50 caractères !")
	private String name;

	@Size(max = 5, message = "L'abréviation dépasse 5 caractères !")
	private String abbreviation;

	@Size(max = 100, message = "L'observation dépasse 100 caractères !")
	private String observation;

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

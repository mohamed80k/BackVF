package com.dto;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LotissementAddDto {

	@NotNull(message = "Le nom est nul !")
	@Size(min = 3, max = 50, message = "Le nom doit être comprise entre 3 et 50 caractères !")
	private String name;

	@Size(max = 200, message = "La description dépasse 200 caractères !")
	private String description;

	@Size(max = 50, message = "La surface dépasse 50 caractères !")
	private String surface;

	@NotNull(message = "La localité est nul !")
	@Valid
	private LocalityDto locality;

	private Set<Integer> tiers;

	@NotNull(message = "Le type de lotissement est nul !")
	private Integer typeLotissement;

	@DecimalMin(value = "0", message = "Le Nombre total des lot est inférieur à 0 !")
	private int nombreTotalLot;
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getSurface() {
		return surface;
	}

	public void setSurface(String surface) {
		this.surface = surface;
	}

	public LocalityDto getLocality() {
		return locality;
	}

	public void setLocality(LocalityDto locality) {
		this.locality = locality;
	}

	public Set<Integer> getTiers() {
		return tiers;
	}

	public void setTiers(Set<Integer> tiers) {
		this.tiers = tiers;
	}

	public Integer getTypeLotissement() {
		return typeLotissement;
	}

	public void setTypeLotissement(Integer typeLotissement) {
		this.typeLotissement = typeLotissement;
	}

	public int getNombreTotalLot() {
		return nombreTotalLot;
	}

	public void setNombreTotalLot(int nombreTotalLot) {
		this.nombreTotalLot = nombreTotalLot;
	}
	
}

package com.dto;

import java.util.List;

public class LotissementInfoDto {

	private Integer id;

	private String name;

	private String description;
	
	private String surface;

	private LocalityDto locality;

	private List<TiersInfoDto> tiers;

	private TypeProjectInfoDto typeLotissement;

	private int nombreTotalLot;    

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

	public List<TiersInfoDto> getTiers() {
		return tiers;
	}

	public void setTiers(List<TiersInfoDto> tiers) {
		this.tiers = tiers;
	}

	public TypeProjectInfoDto getTypeLotissement() {
		return typeLotissement;
	}

	public void setTypeLotissement(TypeProjectInfoDto typeLotissement) {
		this.typeLotissement = typeLotissement;
	}

	public int getNombreTotalLot() {
		return nombreTotalLot;
	}

	public void setNombreTotalLot(int nombreTotalLot) {
		this.nombreTotalLot = nombreTotalLot;
	}

}

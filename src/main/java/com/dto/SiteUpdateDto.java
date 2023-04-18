package com.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SiteUpdateDto {

	@NotNull(message = "L'id est nul !")
	private Integer id;

	@NotNull(message = "La désignation est nul !")
	@Size(min = 3, max = 90, message = "La désignation doit être comprise entre 3 et 50 caractères !")
	private String designation;

	@Size(max = 50, message = "L'abbreviation dépasse 50 caractères !")
	private String abbreviation;

	private boolean sale;

	private boolean expedition;

	@NotNull(message = "La localité est nul !")
	@Valid
	private LocalityDto locality;
    private  Integer region;
	@NotNull(message = "La societé est nul !")
	private Integer society;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public boolean isSale() {
		return sale;
	}

	public void setSale(boolean sale) {
		this.sale = sale;
	}

	public boolean isExpedition() {
		return expedition;
	}

	public void setExpedition(boolean expedition) {
		this.expedition = expedition;
	}

	public LocalityDto getLocality() {
		return locality;
	}

	public void setLocality(LocalityDto locality) {
		this.locality = locality;
	}

	public Integer getSociety() {
		return society;
	}

	public void setSociety(Integer society) {
		this.society = society;
	}

	public Integer getRegion() {
		return region;
	}

	public void setRegion(Integer region) {
		this.region = region;
	}
}

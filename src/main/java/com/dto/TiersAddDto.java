package com.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TiersAddDto {

	@NotNull(message = "Le nom de tiers est nul !")
	@Size(min = 3, max = 50, message = "Le nom de tiers doit être comprise entre 3 et 50 caractères !")
	private String name;

	@Size(max = 8, message = "Le cin dépasse 8 caractères !")
	private String cin;

	@Size(max = 15, message = "Le téléphone 1 dépasse 15 caractères !")
	private String phone1;

	@Size(max = 15, message = "Le téléphone 2 dépasse 15 caractères !")
	private String phone2;

	@Size(max = 15, message = "la taille du fax tier est supérieure à 15 caractères !")
	private String fax;

	@Size(max = 50, message = "L'émail dépasse 50 caractères !")
	private String email;

	@Size(max = 50, message = "Le rib dépasse 50 caractères !")
	private String rib;

	@Size(max = 50, message = "Le patent dépasse 50 caractères !")
	private String patent;

	private Date createAt;

	@NotNull(message = "Le type de tiers est nul !")
	private Integer typeTiers;

	@NotNull(message = "La localité est nul !")
	@Valid
	private LocalityDto locality;

	private List<Integer> commercials;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRib() {
		return rib;
	}

	public void setRib(String rib) {
		this.rib = rib;
	}

	public String getPatent() {
		return patent;
	}

	public void setPatent(String patent) {
		this.patent = patent;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Integer getTypeTiers() {
		return typeTiers;
	}

	public void setTypeTiers(Integer typeTiers) {
		this.typeTiers = typeTiers;
	}

	public LocalityDto getLocality() {
		return locality;
	}

	public void setLocality(LocalityDto locality) {
		this.locality = locality;
	}

	public List<Integer> getCommercials() {
		return commercials;
	}

	public void setCommercials(List<Integer> commercials) {
		this.commercials = commercials;
	}

}

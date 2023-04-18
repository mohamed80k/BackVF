package com.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CustomerUpdateDto {

	@NotNull(message = "L'id est nul !")
	private Integer id;

	@NotNull(message = "Le nom est nul !")
	@Size(min = 3, max = 50, message = "Le nom doit être comprise entre 3 et 50 caractères !")
	private String name;

	@Size(max = 8, message = "Le cin dépasse à 8 caractères !")
	private String cin;

	@Size(max = 10, message = "Le téléphone client dépasse 10 caractères !")
	private String phone1;

	@Size(max = 10, message = "Le téléphone dépasse 10 caractères !")
	private String phone2;

	@Size(max = 10, message = "Le fax dépasse 10 caractères !")
	private String fax;

	@Size(max = 50, message = "L'email dépasse 50 caractères !")
	private String email;

	@Size(max = 50, message = "Le rib dépasse 50 caractères !")
	private String rib;

	@Size(max = 50, message = "Le patent dépasse 50 caractères !")
	private String patent;

	private Date createAt;

	@NotNull(message = "Type de client est nul !")
	private Integer typeCustomer;

	@Size(max = 20, message = "la taille du code client est supérieure à 20 !")
	private String code;

	@NotNull(message = "Localité de client est nul !")
	@Valid
	private LocalityDto locality;

	private List<Integer> commercials;

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

	public Integer getTypeCustomer() {
		return typeCustomer;
	}

	public void setTypeCustomer(Integer typeCustomer) {
		this.typeCustomer = typeCustomer;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

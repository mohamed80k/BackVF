package com.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ContactUpdateDto {

	@NotNull(message = "L'id est nul !")
	private Integer id;

	@NotNull(message = "Le nom est nul !")
	@Size(min = 3, max = 50, message = "Le nom doit être comprise entre 3 et 50 caractères !")
	private String name;

	@Size(max = 10, message = "Le téléphone dépasse 10 caractères !")
	private String phone1;

	@Size(max = 10, message = "Le téléphone dépasse 10 caractères !")
	private String phone2;

	@Size(max = 50, message = "L'émail dépasse 50 caractères !")
	private String email;

	@NotNull(message = "Le client est nul !")
	private Integer customer;

	@NotNull(message = "Le profession est nul !")
	private Integer profession;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getCustomer() {
		return customer;
	}

	public void setCustomer(Integer customer) {
		this.customer = customer;
	}

	public Integer getProfession() {
		return profession;
	}

	public void setProfession(Integer profession) {
		this.profession = profession;
	}

}

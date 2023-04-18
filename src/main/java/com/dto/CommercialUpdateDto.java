package com.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommercialUpdateDto {

	@NotNull(message = "Id de commercial est nul !")
	private Integer id;

	@NotNull(message = "Le nom est nul !")
	@Size(min = 3, max = 50, message = "Le nom doit être comprise entre 3 et 50 caractères !")
	private String name;

	@Size(max = 200, message = "Le nom dépasse 200 caractères!")
	private String address;

	@Size(max = 50, message = "L'email dépasse 50 caractères !")
	private String email;

	@Size(max = 10, message = "Le téléphone dépasse 10 caractères !")
	private String phone;

//	@NotNull(message = "Le site est nul !")
//	private Set<Integer> sites;
	@NotNull(message = "region est null")
	private Integer region;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

//	public Set<Integer> getSites() {
//		return sites;
//	}
//
//	public void setSites(Set<Integer> sites) {
//		this.sites = sites;
//	}

	public Integer getRegion() {
		return region;
	}

	public void setRegion(Integer region) {
		this.region = region;
	}
}

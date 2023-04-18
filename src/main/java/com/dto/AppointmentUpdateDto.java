package com.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AppointmentUpdateDto {

	@NotNull(message = "L'id est nul !")
	private Integer id;

	@NotNull(message = "Le commercial est nul !")
	private Integer commercial;

	@NotNull(message = "Le tiers est nul !")
	private Integer tiers;

	@Size(max = 250, message = "La description dépasse 250 caractères !")
	@NotNull(message = "Description est nul !")
	private String description;

	@Size(max = 200, message = "L'adresse dépasse 200 caractères !")
	private String address;

	@NotNull(message = "Date de rendez-vous est nul !")
	private Date date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCommercial() {
		return commercial;
	}

	public void setCommercial(Integer commercial) {
		this.commercial = commercial;
	}

	public Integer getTiers() {
		return tiers;
	}

	public void setTiers(Integer tiers) {
		this.tiers = tiers;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}

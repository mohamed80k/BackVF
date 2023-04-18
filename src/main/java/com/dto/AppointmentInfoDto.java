package com.dto;

import java.util.Date;

public class AppointmentInfoDto {

	private Integer id;

	private CommercialInfoDto commercial;

	private TiersInfoDto tiers;

	private String description;

	private String address;

	private Date date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CommercialInfoDto getCommercial() {
		return commercial;
	}

	public void setCommercial(CommercialInfoDto commercial) {
		this.commercial = commercial;
	}

	public TiersInfoDto getTiers() {
		return tiers;
	}

	public void setTiers(TiersInfoDto tiers) {
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

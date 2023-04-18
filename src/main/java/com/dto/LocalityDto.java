package com.dto;

import javax.validation.constraints.Size;

public class LocalityDto {

	private Integer id;

	@Size(min = 3, max = 50, message = "Le nom de localité doit être comprise entre 3 et 50 caractères !")
	private String name;

	//@DecimalMin(value = "0", message = "La latitude de localité est inférieur à 0 !")
	private double latitude;

	//@DecimalMax(value = "0", message = "longitude de localité est supérieur à 0 !")
	private double longitude;

	private String address;

	public LocalityDto(Integer id, String name, double latitude, double longitude, String address) {
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
	}

	public LocalityDto() {

	}

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

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}

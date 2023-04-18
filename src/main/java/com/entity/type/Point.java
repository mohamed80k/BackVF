package com.entity.type;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Point {

	@JsonProperty("lng")
	private double longitude;
	
	@JsonProperty("lat")
	private double latitude;

	public Point() {
		// TODO Auto-generated constructor stub
	}

	public Point(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "Point [longitude=" + longitude + ", latitude=" + latitude + "]";
	}

}
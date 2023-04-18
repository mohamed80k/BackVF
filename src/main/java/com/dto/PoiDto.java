/**
 * 
 */
package com.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.entity.type.Point;
import com.entity.type.PointInterestType;
import com.fasterxml.jackson.annotation.JsonSetter;

public class PoiDto {
	

	private long idPointInterest;

	private String name;

	private String address;

	private int ray;

	private long zoomLevel;

	private String imageUri;

	private PointInterestType type;

	private Point coordinate;

	private String hash;
	
	private String plyColor;

	private Set<CommercialInfoDto> commercials;
	
	private Set<CustomerInfoDto> customers;
	
	public PoiDto() {
		super();
	}

	public Set<CommercialInfoDto> getCommercials() {
		return commercials;
	}

	public void setCommercials(Set<CommercialInfoDto> commercials) {
		this.commercials = commercials;
	}

	public Set<CustomerInfoDto> getCustomers() {
		return customers;
	}

	public void setCustomers(Set<CustomerInfoDto> customers) {
		this.customers = customers;
	}

	public String getPlyColor() {
		return plyColor;
	}

	public void setPlyColor(String plyColor) {
		this.plyColor = plyColor;
	}
	

	public long getIdPointInterest() {
		return idPointInterest;
	}

	public void setIdPointInterest(long idPointInterest) {
		this.idPointInterest = idPointInterest;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Point coordinate) {
		this.coordinate = coordinate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRay() {
		return ray;
	}

	public void setRay(int ray) {
		this.ray = ray;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public long getZoomLevel() {
		return zoomLevel;
	}

	public void setZoomLevel(long zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

	public PointInterestType getType() {
		return type;
	}

	public void setType(PointInterestType type) {
		this.type = type;
	}

	public String getHash() {
		return hash;
	}

	@JsonSetter
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	// 33.704369068527406,-7.389673590660095;33.70400312448535,-7.389874756336212;33.70386924212924,-7.389880120754242;33.7036550299254,-7.38981306552887;33.704074528323304,-7.389064729213715;33.704145932101895,-7.3890432715415955
	public List<Point> getDecode() {

		List<Point> points = new ArrayList<Point>();

		if (this.type == PointInterestType.POLYGON) {
			String[] hashPoints = this.hash.split(";");
			for (int i = 0; i < hashPoints.length; i++) {

				String[] hashPoint = hashPoints[i].split(",");
				
				Point point = new Point(Double.parseDouble(hashPoint[1]) ,Double.parseDouble(hashPoint[0]));//
				points.add(point);
			}
		}

		return points;
	}


}

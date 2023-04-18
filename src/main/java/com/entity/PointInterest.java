package com.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.entity.type.Point;
import com.entity.type.PointInterestType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;


@Entity
@Table(name = "point_interest")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PointInterest implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "POINT_INTEREST_ID")
	private long idPointInterest;

	@Column(name = "name")
	private String name;

	@Column(name = "address")
	private String address;

	@Column(name = "ray")
	private int ray;

	@Column(name = "zoom_level")
	private long zoomLevel;

	@Column(name = "image_uri")
	private String imageUri;

	@Enumerated(EnumType.ORDINAL)
	private PointInterestType type;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "lat", column = @Column(name = "x")),
			@AttributeOverride(name = "lng", column = @Column(name = "y")) })
	private Point coordinate;

	@Lob
	private String hash;
	
	@Column(name = "polygone_color", length = 20)
	private String plyColor;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "point_interest_commercial", joinColumns = { @JoinColumn(name = "point_interest_id") }, inverseJoinColumns = {
			@JoinColumn(name = "commercial_id") })
	private Set<Commercial> commercials;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "point_interest_customer", joinColumns = { @JoinColumn(name = "point_interest_id") }, inverseJoinColumns = {
			@JoinColumn(name = "customer_id") })
	private Set<Customer> customers;

	public PointInterest() {
		super();
	}

	public PointInterest(String name, String address, int ray, long niveauZoom, Point coordinate, String imageUri, Set<Commercial> commercials, Set<Customer> customers) {
		super();
		this.name = name;
		this.address = address;
		this.ray = ray;
		this.zoomLevel = niveauZoom;
		this.coordinate = coordinate;
		this.imageUri = imageUri;
		this.commercials = commercials;
		this.customers = customers;
	}
	
	public PointInterest(String name, String address, int ray, long niveauZoom, Point coordinate, String imageUri, String plyColor, Set<Commercial> commercials, Set<Customer> customers) {
		super();
		this.name = name;
		this.address = address;
		this.ray = ray;
		this.zoomLevel = niveauZoom;
		this.coordinate = coordinate;
		this.imageUri = imageUri;
		this.plyColor = plyColor;
		this.commercials = commercials;
		this.customers = customers;
	}

	public Set<Commercial> getCommercials() {
		return commercials;
	}

	public void setCommercials(Set<Commercial> commercials) {
		this.commercials = commercials;
	}

	public Set<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Set<Customer> customers) {
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

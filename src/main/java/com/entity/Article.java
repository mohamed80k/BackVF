package com.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "article")
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "reference")
	private String reference;

	@Column(name = "designation")
	private String designation;

	@Column(name = "price")
	private double price;

	@Column(name = "length")
	private double length;

	@Column(name = "weight")
	private double weight;

	@Column(name = "unit")
	private String unit;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private Category category;
	
//	@ManyToMany(mappedBy = "articles", fetch = FetchType.LAZY)
//	private Set<Sale> sales;
	
//	@ManyToMany(mappedBy = "articles", fetch = FetchType.LAZY)
//	private Set<PointOfSale> pointOfSales;

	public Article() {
	}

	public Article(Integer id, String reference, String designation, double price, double length, double weight, String unit, Category category) {
		this.id = id;
		this.reference = reference;
		this.designation = designation;
		this.price = price;
		this.length = length;
		this.weight = weight;
		this.unit = unit;
		this.category = category;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


	
	

}

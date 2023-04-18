package com.dto;

public class ArticleInfoDto {

	private Integer id;

	private String reference;

	private String designation;

	private double price;

	private double length;

	private double weight;

	private String unit;

	private CategoryInfoDto category;

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

	public CategoryInfoDto getCategory() {
		return category;
	}

	public void setCategory(CategoryInfoDto category) {
		this.category = category;
	}

}

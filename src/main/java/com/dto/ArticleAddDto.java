package com.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ArticleAddDto {

	@NotNull(message = "La référence est nul !")
	@Size(min = 2, max = 100, message = "La référence doit être comprise entre 3 et 100 caractères!")
	private String reference;

	@Size(max = 100, message = "La ésignation est supérieure à 100 caractères !")
	private String designation;

	@DecimalMin(value = "0", message = "Le prix est inférieur à 0 !")
	private double price;

	@DecimalMin(value = "0", message = "Le longueur est inférieur à 0 !")
	private double length;

	@DecimalMin(value = "0", message = "Le poid est inférieur à 0 !")
	private double weight;

	@Size(max = 5, message = "L'unité dépasse 5 caractères !")
	private String unit;

	@NotNull(message = "La catégorie est nul !")
	private Integer category;

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

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

}

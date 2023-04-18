package com.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "unit")
	private String unit;

	@Column(name = "observation")
	private String observation;

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private Set<Article> articles;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "society_id")
	private Society society;

	public Category() {
	}

	public Category(Integer id, String name, String unit, String observation, Set<Article> articles, Society society) {
		this.id = id;
		this.name = name;
		this.unit = unit;
		this.observation = observation;
		this.articles = articles;
		this.society = society;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	public Society getSociety() {
		return society;
	}

	public void setSociety(Society society) {
		this.society = society;
	}

}

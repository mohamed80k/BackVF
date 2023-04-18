package com.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "type_tiers")
public class TypeTiers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "abbreviation")
	private String abbreviation;

	@Column(name = "observation")
	private String observation;

	@OneToMany(mappedBy = "typeTiers", fetch = FetchType.LAZY)
	private Set<Tiers> tiers;

	public TypeTiers() {
	}

	public TypeTiers(Integer id, String name, String abbreviation, String observation, Set<Tiers> tiers) {
		this.id = id;
		this.name = name;
		this.abbreviation = abbreviation;
		this.observation = observation;
		this.tiers = tiers;
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

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Set<Tiers> getTiers() {
		return tiers;
	}

	public void setTiers(Set<Tiers> tiers) {
		this.tiers = tiers;
	}

}

package com.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lotissement")
public class Lotissement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "surface")
	private String surface;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "locality_id")
	private Locality locality;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "lotissement_tiers", joinColumns = { @JoinColumn(name = "lotissement_id") }, inverseJoinColumns = {
			@JoinColumn(name = "tiers_id") })
	private Set<Tiers> tiers;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "lotissement_type_id")
	private TypeProject typeLotissement;
	
	@Column(name = "nombreTotalLot")
	private int nombreTotalLot;

	public Lotissement() {
	}

	public Lotissement(Integer id, String name, String description, String surface, 
			Locality locality, Set<Tiers> tiers, TypeProject typeLotissement, int nombreTotalLot) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.surface = surface;
		this.locality = locality;
		this.tiers = tiers;
		this.typeLotissement = typeLotissement;
		this.nombreTotalLot = nombreTotalLot;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSurface() {
		return surface;
	}

	public void setSurface(String surface) {
		this.surface = surface;
	}

	public Locality getLocality() {
		return locality;
	}

	public void setLocality(Locality locality) {
		this.locality = locality;
	}

	public Set<Tiers> getTiers() {
		return tiers;
	}

	public void setTiers(Set<Tiers> tiers) {
		this.tiers = tiers;
	}

	public TypeProject getTypeLotissement() {
		return typeLotissement;
	}

	public void setTypeLotissement(TypeProject typeLotissement) {
		this.typeLotissement = typeLotissement;
	}

	public int getNombreTotalLot() {
		return nombreTotalLot;
	}

	public void setNombreTotalLot(int nombreTotalLot) {
		this.nombreTotalLot = nombreTotalLot;
	}
	
}

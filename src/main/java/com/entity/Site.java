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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "site")
public class Site {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "designation")
	private String designation;

	@Column(name = "abbreviation")
	private String abbreviation;

	@Column(name = "sale")
	private boolean sale;

	@Column(name = "expedition")
	private boolean expedition;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "locality_id")
	private Locality locality;

//	@ManyToMany(mappedBy = "sites", fetch = FetchType.LAZY)
//	private Set<Commercial> commercials;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_region")
	private Region regions;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "society_id")
	private Society society;

	@OneToMany(mappedBy = "site", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Email> emails;

//	@OneToMany(mappedBy = "site", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private Set<Sale> sales;

	public Site() {
	}

	public Site(Integer id, String designation, String abbreviation, boolean sale, boolean expedition,
			Locality locality, Region regions,  Society society, Set<Email> emails) {
		this.id = id;
		this.designation = designation;
		this.abbreviation = abbreviation;
		this.sale = sale;
		this.expedition = expedition;
		this.locality = locality;
		this.regions=regions;
		this.society = society;
		this.emails = emails;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public boolean isSale() {
		return sale;
	}

	public void setSale(boolean sale) {
		this.sale = sale;
	}

	public boolean isExpedition() {
		return expedition;
	}

	public void setExpedition(boolean expedition) {
		this.expedition = expedition;
	}

	public Locality getLocality() {
		return locality;
	}

	public void setLocality(Locality locality) {
		this.locality = locality;
	}

	public Region getRegions() {
		return regions;
	}

	public void setRegions(Region regions) {
		this.regions = regions;
	}

	public Society getSociety() {
		return society;
	}

	public void setSociety(Society society) {
		this.society = society;
	}

	public Set<Email> getEmails() {
		return emails;
	}

	public void setEmails(Set<Email> emails) {
		this.emails = emails;
	}



}

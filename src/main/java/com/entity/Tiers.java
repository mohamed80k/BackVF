package com.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tiers")
@Inheritance(strategy = InheritanceType.JOINED)
public class Tiers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "cin")
	private String cin;

	@Column(name = "phone1")
	private String phone1;

	@Column(name = "phone2")
	private String phone2;

	@Column(name = "fax")
	private String fax;

	@Column(name = "email")
	private String email;

	@Column(name = "rib")
	private String rib;

	@Column(name = "patent")
	private String patent;

	@Column(name = "create_at")
	private Date createAt;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tiers_type_id")
	private TypeTiers typeTiers;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "locality_id")
	private Locality locality;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tiers_commercial", joinColumns = { @JoinColumn(name = "tiers_id") }, inverseJoinColumns = {
			@JoinColumn(name = "commercial_id") })
	private Set<Commercial> commercials;

	@ManyToMany(mappedBy = "tiers", fetch = FetchType.LAZY)
	private Set<Project> projects;

	@OneToMany(mappedBy = "tiers", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointments;
	
	@ManyToMany(mappedBy = "tiers", fetch = FetchType.LAZY)
	private Set<Lotissement> lotissements;
	
//	@ManyToMany(mappedBy = "tiers", fetch = FetchType.LAZY)
//	private Set<PointOfSale> pointOfSales;

	public Tiers() {
	}

	public Tiers(Integer id, String name, String cin, String phone1, String phone2, String fax, String email,
			String rib, String patent, Date createAt, TypeTiers typeTiers, Locality locality,
			Set<Commercial> commercials, Set<Project> projects, Set<Appointment> appointments) {
		this.id = id;
		this.name = name;
		this.cin = cin;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.fax = fax;
		this.email = email;
		this.rib = rib;
		this.patent = patent;
		this.createAt = createAt;
		this.typeTiers = typeTiers;
		this.locality = locality;
		this.commercials = commercials;
		this.projects = projects;
		this.appointments = appointments;
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

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRib() {
		return rib;
	}

	public void setRib(String rib) {
		this.rib = rib;
	}

	public String getPatent() {
		return patent;
	}

	public void setPatent(String patent) {
		this.patent = patent;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public TypeTiers getTypeTiers() {
		return typeTiers;
	}

	public void setTypeTiers(TypeTiers typeTiers) {
		this.typeTiers = typeTiers;
	}

	public Locality getLocality() {
		return locality;
	}

	public void setLocality(Locality locality) {
		this.locality = locality;
	}

	public Set<Commercial> getCommercials() {
		return commercials;
	}

	public void setCommercials(Set<Commercial> commercials) {
		this.commercials = commercials;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	
	public Set<Lotissement> getLotissements() {
		return lotissements;
	}

	public void setLotissements(Set<Lotissement> lotissements) {
		this.lotissements = lotissements;
	}

//	public Set<PointOfSale> getPointOfSales() {
//		return pointOfSales;
//	}
//
//	public void setPointOfSales(Set<PointOfSale> pointOfSales) {
//		this.pointOfSales = pointOfSales;
//	}

}

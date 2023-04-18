package com.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "tiers_id")
public class Customer extends Tiers {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_type_id")
	private TypeCustomer typeCustomer;

	@Column(name = "code")
	private String code;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Contact> contacts;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Decision> decisions;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<FollowerProject> followerProjects;

//	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private Set<Sale> sales;

	public Customer() {
		super();
	}

	public Customer(Integer id, String name, String cin, String phone1, String phone2, String fax, String email,
			String rib, String patent, Date createAt, TypeTiers typeTier, Locality locality,
			Set<Commercial> commercials, Set<Project> projects, Set<Appointment> appointments,
			TypeCustomer typeCustomer, String code, Set<Contact> contacts, Set<Decision> decisions,
			Set<FollowerProject> followerProjects) {
		super(id, name, cin, phone1, phone2, fax, email, rib, patent, createAt, typeTier, locality, commercials,
				projects, appointments);
		this.typeCustomer = typeCustomer;
		this.code = code;
		this.contacts = contacts;
		this.decisions = decisions;
		this.followerProjects = followerProjects;

	}

	public TypeCustomer getTypeCustomer() {
		return typeCustomer;
	}

	public void setTypeCustomer(TypeCustomer typeCustomer) {
		this.typeCustomer = typeCustomer;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	public Set<Decision> getDecisions() {
		return decisions;
	}

	public void setDecisions(Set<Decision> decisions) {
		this.decisions = decisions;
	}

	public Set<FollowerProject> getFollowerProjects() {
		return followerProjects;
	}

	public void setFollowerProjects(Set<FollowerProject> followerProjects) {
		this.followerProjects = followerProjects;
	}



}

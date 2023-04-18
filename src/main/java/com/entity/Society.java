package com.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "society")
public class Society {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

//	@Column(name = "order")
//	private byte order;

	@OneToMany(mappedBy = "society", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Site> sites;

	@OneToMany(mappedBy = "society", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Category> categories;

	public Society() {
	}

	public Society(Integer id, String name, Set<Site> sites, Set<Category> categories) {
		this.id = id;
		this.name = name;
//		this.order = order;
		this.sites = sites;
		this.categories = categories;
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

//	public byte getOrder() {
//		return order;
//	}
//
//	public void setOrder(byte order) {
//		this.order = order;
//	}

	public Set<Site> getSites() {
		return sites;
	}

	public void setSites(Set<Site> sites) {
		this.sites = sites;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

}

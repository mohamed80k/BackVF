package com.dto;

import java.util.Date;
import java.util.Set;

public class TiersInfoDto {

	private Integer id;

	private String name;

	private String cin;

	private String phone1;

	private String phone2;

	private String fax;

	private String email;

	private String rib;

	private String patent;

	private Date createAt;

	private TypeTiersInfoDto typeTiers;

	private TypeCustomerInfoDto typeCustomer;

	private LocalityDto locality;

	private Set<CommercialInfoDto> commercials;

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

	public TypeTiersInfoDto getTypeTiers() {
		return typeTiers;
	}

	public void setTypeTiers(TypeTiersInfoDto typeTiers) {
		this.typeTiers = typeTiers;
	}

	public TypeCustomerInfoDto getTypeCustomer() {
		return typeCustomer;
	}

	public void setTypeCustomer(TypeCustomerInfoDto typeCustomer) {
		this.typeCustomer = typeCustomer;
	}

	public LocalityDto getLocality() {
		return locality;
	}

	public void setLocality(LocalityDto locality) {
		this.locality = locality;
	}

	public Set<CommercialInfoDto> getCommercials() {
		return commercials;
	}

	public void setCommercials(Set<CommercialInfoDto> commercials) {
		this.commercials = commercials;
	}

}

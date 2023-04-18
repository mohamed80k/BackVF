package com.dto;

import java.util.Set;

public class CommercialInfoDto {

	private Integer id;

	private String name;

	private String address;

	private String email;

	private String phone;
	private RegionInfoDto region;
//	private Set<SiteInfoDto> sites;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

//	public Set<SiteInfoDto> getSites() {
//		return sites;
//	}
//
//	public void setSites(Set<SiteInfoDto> sites) {
//		this.sites = sites;
//	}

	public RegionInfoDto getRegion() {
		return region;
	}

	public void setRegion(RegionInfoDto region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return "CommercialInfoDto{" +
				"id=" + id +
				", name='" + name + '\'' +
				", address='" + address + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				", region=" + region.toString() +
				'}';
	}
}

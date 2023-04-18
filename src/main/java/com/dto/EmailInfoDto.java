package com.dto;

public class EmailInfoDto {

	private Integer id;

	private String type;

	private String email1;

	private String email2;

	private SiteInfoDto site;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public SiteInfoDto getSite() {
		return site;
	}

	public void setSite(SiteInfoDto site) {
		this.site = site;
	}

}

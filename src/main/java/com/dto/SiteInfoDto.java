package com.dto;

public class SiteInfoDto {

	private Integer id;

	private String designation;

	private String abbreviation;

	private boolean sale;

	private boolean expedition;

	private LocalityDto locality;
	private RegionInfoDto region;
	private SocietyInfoDto society;

	public SiteInfoDto(Integer id, String designation, String abbreviation, boolean sale, boolean expedition, LocalityDto locality, RegionInfoDto region, SocietyInfoDto society) {
		this.id = id;
		this.designation = designation;
		this.abbreviation = abbreviation;
		this.sale = sale;
		this.expedition = expedition;
		this.locality = locality;
		this.region = region;
		this.society = society;
	}

	public SiteInfoDto() {
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

	public LocalityDto getLocality() {
		return locality;
	}

	public void setLocality(LocalityDto locality) {
		this.locality = locality;
	}

	public SocietyInfoDto getSociety() {
		return society;
	}

	public void setSociety(SocietyInfoDto society) {
		this.society = society;
	}

	public RegionInfoDto getRegion() {
		return region;
	}

	public void setRegion(RegionInfoDto region) {
		this.region = region;
	}
}

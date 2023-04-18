package com.dto;

import java.util.Date;
import java.util.List;

import com.entity.type.DasProject;
import com.entity.type.SegmentProject;
import com.entity.type.StatusProject;

public class ProjectInfoDto {

	private Integer id;

	private String name;

	private String description;

	private StatusProject status;

	private SegmentProject segment;

	private List<DasProject> das;

	private String surface;

	private String trSurface;

	private Date createAt;

	private String businessNumber;

	private LocalityDto locality;

	private List<CommercialInfoDto> commercials;

	private List<TiersInfoDto> tiers;

	private TypeProjectInfoDto typeProject;

	private StateProjectInfoDto stateProject;

	private SiteInfoDto siteProject;

	private LotissementInfoDto projectLotissement;

	private double caEstime;

	private double caBpe;
	
	private double caPl;
	
	private double caMa;
	
	private double caPvDa;
	
	private String previousStatus;

	private Date dateChangedStatus;

	private Date closedAt;

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

	public StatusProject getStatus() {
		return status;
	}

	public void setStatus(StatusProject status) {
		this.status = status;
	}

	public String getSurface() {
		return surface;
	}

	public void setSurface(String surface) {
		this.surface = surface;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getBusinessNumber() {
		return businessNumber;
	}

	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}

	public LocalityDto getLocality() {
		return locality;
	}

	public void setLocality(LocalityDto locality) {
		this.locality = locality;
	}

	public List<CommercialInfoDto> getCommercials() {
		return commercials;
	}

	public void setCommercials(List<CommercialInfoDto> commercials) {
		this.commercials = commercials;
	}

	public List<TiersInfoDto> getTiers() {
		return tiers;
	}

	public void setTiers(List<TiersInfoDto> tiers) {
		this.tiers = tiers;
	}

	public TypeProjectInfoDto getTypeProject() {
		return typeProject;
	}

	public void setTypeProject(TypeProjectInfoDto typeProject) {
		this.typeProject = typeProject;
	}

	public StateProjectInfoDto getStateProject() {
		return stateProject;
	}

	public void setStateProject(StateProjectInfoDto stateProject) {
		this.stateProject = stateProject;
	}

	public SiteInfoDto getSiteProject() {
		return siteProject;
	}

	public void setSiteProject(SiteInfoDto siteProject) {
		this.siteProject = siteProject;
	}

	public List<DasProject> getDas() {
		return das;
	}

	public void setDas(List<DasProject> das) {
		this.das = das;
	}

	public double getCaEstime() {
		return caEstime;
	}

	public void setCaEstime(double caEstime) {
		this.caEstime = caEstime;
	}

	public LotissementInfoDto getProjectLotissement() {
		return projectLotissement;
	}

	public void setProjectLotissement(LotissementInfoDto projectLotissement) {
		this.projectLotissement = projectLotissement;
	}

	public String getPreviousStatus() {
		return previousStatus;
	}

	public void setPreviousStatus(String previousStatus) {
		this.previousStatus = previousStatus;
	}

	public Date getDateChangedStatus() {
		return dateChangedStatus;
	}

	public void setDateChangedStatus(Date dateChangedStatus) {
		this.dateChangedStatus = dateChangedStatus;
	}

	public Date getClosedAt() {
		return closedAt;
	}

	public void setClosedAt(Date closedAt) {
		this.closedAt = closedAt;
	}

	public SegmentProject getSegment() {
		return segment;
	}

	public void setSegment(SegmentProject segment) {
		this.segment = segment;
	}

	public String getTrSurface() {
		return trSurface;
	}

	public void setTrSurface(String trSurface) {
		this.trSurface = trSurface;
	}

	public double getCaBpe() {
		return caBpe;
	}

	public void setCaBpe(double caBpe) {
		this.caBpe = caBpe;
	}

	public double getCaPl() {
		return caPl;
	}

	public void setCaPl(double caPl) {
		this.caPl = caPl;
	}

	public double getCaMa() {
		return caMa;
	}

	public void setCaMa(double caMa) {
		this.caMa = caMa;
	}

	public double getCaPvDa() {
		return caPvDa;
	}

	public void setCaPvDa(double caPvDa) {
		this.caPvDa = caPvDa;
	}
	
}

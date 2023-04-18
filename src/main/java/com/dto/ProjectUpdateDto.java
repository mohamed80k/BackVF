package com.dto;

import java.util.Date;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.entity.type.DasProject;
import com.entity.type.SegmentProject;
import com.entity.type.StatusProject;

public class ProjectUpdateDto {

	@NotNull(message = "L'id est nul !")
	private Integer id;

	@NotNull(message = "Le nom est nul !")
	@Size(min = 3, max = 50, message = "Le nom doit être comprise entre 3 et 50 caractères !")
	private String name;

	@Size(max = 200, message = "La description dépasse 200 caractères !")
	private String description;

	@NotNull(message = "Le statut est nul !")
	private StatusProject status;
	
	@NotNull(message = "Le segment est nul !")
	private SegmentProject segment;
	
	private Set<DasProject> das;

	@Size(max = 50, message = "La surface dépasse 50 caractères !")
	private String surface;
	
	@Size(max = 50, message = "La surface théorique dépasse 50 caractères !")
	private String trSurface;

	private Date createAt;

	@Size(max = 20, message = "Le numéro d'affaire dépasse 20 caractères !")
	private String businessNumber;

	@NotNull(message = "La localité est nul !")
	@Valid
	private LocalityDto locality;

	private Set<Integer> commercials;

	private Set<Integer> tiers;

	@NotNull(message = "Le type de projet est nul !")
	private Integer typeProject;

	@NotNull(message = "L'etat de projet est nul !")
	private Integer stateProject;
	
	@NotNull(message = "Le site de projet est nul !")
	private Integer siteProject;
	
	@DecimalMin(value = "0", message = "Le C.A estimé est inférieur à 0 !")
	private double caEstime;
	
	@DecimalMin(value = "0", message = "BPE est inférieur à 0 !")
	private double caBpe;
	
	@DecimalMin(value = "0", message = "PL est inférieur à 0 !")
	private double caPl;
	
	@DecimalMin(value = "0", message = "Maçonnerie est inférieur à 0 !")
	private double caMa;
	
	@DecimalMin(value = "0", message = "PV/Dalle est inférieur à 0 !")
	private double caPvDa;
	
	private Integer projectLotissement;
	
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

	public void setSurface(String surface) {
		this.surface = surface;
	}

	public LocalityDto getLocality() {
		return locality;
	}

	public void setLocality(LocalityDto locality) {
		this.locality = locality;
	}

	public Set<Integer> getCommercials() {
		return commercials;
	}

	public void setCommercials(Set<Integer> commercials) {
		this.commercials = commercials;
	}

	public Set<Integer> getTiers() {
		return tiers;
	}

	public void setTiers(Set<Integer> tiers) {
		this.tiers = tiers;
	}

	public Integer getTypeProject() {
		return typeProject;
	}

	public void setTypeProject(Integer typeProject) {
		this.typeProject = typeProject;
	}

	public Integer getStateProject() {
		return stateProject;
	}

	public void setStateProject(Integer stateProject) {
		this.stateProject = stateProject;
	}

	public Integer getSiteProject() {
		return siteProject;
	}

	public void setSiteProject(Integer siteProject) {
		this.siteProject = siteProject;
	}

	public Set<DasProject> getDas() {
		return das;
	}

	public void setDas(Set<DasProject> das) {
		this.das = das;
	}

	public double getCaEstime() {
		return caEstime;
	}

	public void setCaEstime(double caEstime) {
		this.caEstime = caEstime;
	}

	public Integer getProjectLotissement() {
		return projectLotissement;
	}

	public void setProjectLotissement(Integer projectLotissement) {
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

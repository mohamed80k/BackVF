package com.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.entity.type.DasProject;
import com.entity.type.SegmentProject;
import com.entity.type.StatusProject;

@Entity
@Table(name = "project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusProject status;
	
	@Column(name = "das")
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "das", joinColumns = @JoinColumn(name = "id"))
	@Enumerated(EnumType.STRING)
	private Set<DasProject> das;
	
	@Column(name = "caEstime")
	private double caEstime;
	
	@Column(name = "caBpe")
	private double caBpe;
	
	@Column(name = "caPl")
	private double caPl;
	
	@Column(name = "caMa")
	private double caMa;
	
	@Column(name = "caPvDa")
	private double caPvDa;

	@Column(name = "surface")
	private String surface;
	
	@Column(name = "trSurface")
	private String trSurface;

	@Column(name = "create_at")
	private Date createAt;

	@Column(name = "business_number")
	private String businessNumber;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "locality_id")
	private Locality locality;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "project_commercial", joinColumns = { @JoinColumn(name = "project_id") }, inverseJoinColumns = {
			@JoinColumn(name = "commercial_id") })
	private Set<Commercial> commercials;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "project_tiers", joinColumns = { @JoinColumn(name = "project_id") }, inverseJoinColumns = {
			@JoinColumn(name = "tiers_id") })
	private Set<Tiers> tiers;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_type_id")
	private TypeProject typeProject;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_site_id")
	private Site siteProject;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_state_id")
	private StateProject stateProject;

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Decision> decisions;

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<FollowerProject> followerProjects;

//	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private Set<Sale> sales;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_lotissement_id")
	private Lotissement projectLotissement;
	
	@Column(name = "previous_status", length = 30)
	private String previousStatus;
	
	@Column(name = "date_changed_status")
    private Date dateChangedStatus;
	
	@Column(name = "closed_at")
	private Date closedAt;
	
	@Column(name = "segment")
	@Enumerated(EnumType.STRING)
	private SegmentProject segment;

	public Project() {
	}

	public Project(Integer id, String name, String description, StatusProject status, String surface, String trSurface, Date createAt,
			String businessNumber, Locality locality, Set<Commercial> commercials, Set<Tiers> tiers,
			TypeProject typeProject, StateProject stateProject, Set<Decision> decisions,
			Set<FollowerProject> followerProjects,  Site siteProject,
			Set<DasProject> das, double caEstime , double caBpe, double caPl, double caMa, double caPvDa, Lotissement projectLotissement, SegmentProject segment) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.surface = surface;
		this.createAt = createAt;
		this.businessNumber = businessNumber;
		this.locality = locality;
		this.commercials = commercials;
		this.tiers = tiers;
		this.typeProject = typeProject;
		this.stateProject = stateProject;
		this.decisions = decisions;
		this.followerProjects = followerProjects;
		this.siteProject = siteProject;
		this.das = das;
		this.caEstime = caEstime;
		this.projectLotissement = projectLotissement;
		this.segment = segment;
		this.trSurface = trSurface;
		this.caBpe = caBpe;
        this.caPl = caPl;
        this.caMa = caMa;
        this.caPvDa = caPvDa;
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

	public Set<Tiers> getTiers() {
		return tiers;
	}

	public void setTiers(Set<Tiers> tiers) {
		this.tiers = tiers;
	}

	public TypeProject getTypeProject() {
		return typeProject;
	}

	public void setTypeProject(TypeProject typeProject) {
		this.typeProject = typeProject;
	}

	public StateProject getStateProject() {
		return stateProject;
	}

	public void setStateProject(StateProject stateProject) {
		this.stateProject = stateProject;
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

	public Site getSiteProject() {
		return siteProject;
	}

	public void setSiteProject(Site siteProject) {
		this.siteProject = siteProject;
	}

	public double getCaEstime() {
		return caEstime;
	}

	public void setCaEstime(double caEstime) {
		this.caEstime = caEstime;
	}

	public Lotissement getProjectLotissement() {
		return projectLotissement;
	}

	public void setProjectLotissement(Lotissement projectLotissement) {
		this.projectLotissement = projectLotissement;
	}

	public Set<DasProject> getDas() {
		return das;
	}

	public void setDas(Set<DasProject> das) {
		this.das = das;
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

	public String getTrSurface() {
		return trSurface;
	}

	public void setTrSurface(String trSurface) {
		this.trSurface = trSurface;
	}
	
	
	
}

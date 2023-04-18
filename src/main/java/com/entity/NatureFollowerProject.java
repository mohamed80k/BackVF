package com.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "nature_follower_project")
public class NatureFollowerProject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "abbreviation")
	private String abbreviation;

	@Column(name = "observation")
	private String observation;

	@OneToMany(mappedBy = "natureFollower", fetch = FetchType.LAZY)
	private Set<FollowerProject> followerProjects;

	public NatureFollowerProject() {
	}

	public NatureFollowerProject(Integer id, String name, String abbreviation, String observation,
			Set<FollowerProject> followerProjects) {
		this.id = id;
		this.name = name;
		this.abbreviation = abbreviation;
		this.observation = observation;
		this.followerProjects = followerProjects;
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

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Set<FollowerProject> getFollowerProjects() {
		return followerProjects;
	}

	public void setFollowerProjects(Set<FollowerProject> followerProjects) {
		this.followerProjects = followerProjects;
	}

}

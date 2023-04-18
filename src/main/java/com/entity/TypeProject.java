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
@Table(name = "type_project")
public class TypeProject {

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

	@OneToMany(mappedBy = "typeProject", fetch = FetchType.LAZY)
	private Set<Project> projects;
	
	@OneToMany(mappedBy = "typeLotissement", fetch = FetchType.LAZY)
	private Set<Lotissement> lotissements;

	public TypeProject() {
	}

	public TypeProject(Integer id, String name, String abbreviation, String observation, Set<Project> projects, Set<Lotissement>lotissements) {
		this.id = id;
		this.name = name;
		this.abbreviation = abbreviation;
		this.observation = observation;
		this.projects = projects;
		this.lotissements = lotissements;
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

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public Set<Lotissement> getLotissements() {
		return lotissements;
	}

	public void setLotissements(Set<Lotissement> lotissements) {
		this.lotissements = lotissements;
	}
	
	

}

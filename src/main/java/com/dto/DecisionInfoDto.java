package com.dto;

import java.util.Date;

public class DecisionInfoDto {

	private Integer id;

	private String name;

	private Date createAt;

	private Date decideAt;

	private boolean done;

	private ProjectInfoDto project;

	private CommercialInfoDto commercial;

	private CustomerInfoDto customer;

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

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getDecideAt() {
		return decideAt;
	}

	public void setDecideAt(Date decideAt) {
		this.decideAt = decideAt;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public ProjectInfoDto getProject() {
		return project;
	}

	public void setProject(ProjectInfoDto project) {
		this.project = project;
	}

	public CommercialInfoDto getCommercial() {
		return commercial;
	}

	public void setCommercial(CommercialInfoDto commercial) {
		this.commercial = commercial;
	}

	public CustomerInfoDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerInfoDto customer) {
		this.customer = customer;
	}

}

package com.dto;

import java.util.Date;

public class FollowerProjectInfoDto {

	private Integer id;

	private String object;

	private String note;

	private Date createAt;

	private Date day;

	private ProjectInfoDto project;

	private CommercialInfoDto commercial;

	private CustomerInfoDto customer;

	private NatureFollowerProjectInfoDto natureFollower;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
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

	public NatureFollowerProjectInfoDto getNatureFollower() {
		return natureFollower;
	}

	public void setNatureFollower(NatureFollowerProjectInfoDto natureFollower) {
		this.natureFollower = natureFollower;
	}

}

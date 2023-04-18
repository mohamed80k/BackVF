package com.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FollowerProjectUpdateDto {

	@NotNull(message = "L'id est nul !")
	private Integer id;

	@Size(min = 3, max = 100, message = "L'objet doit être comprise entre 3 et 100 caractères !")
	private String object;

	@Size(max = 2000, message = "La note dépasse 2000 caractères  !")
	private String note;

	private Date createAt;

	@NotNull(message = "Le projet est nul !")
	private Integer project;

	@NotNull(message = "Le commercial est nul !")
	private Integer commercial;

	private Integer customer;

	@NotNull(message = "La nature de suivi de projet est nul !")
	private Integer natureFollower;

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

	public Integer getProject() {
		return project;
	}

	public void setProject(Integer project) {
		this.project = project;
	}

	public Integer getCommercial() {
		return commercial;
	}

	public void setCommercial(Integer commercial) {
		this.commercial = commercial;
	}

	public Integer getCustomer() {
		return customer;
	}

	public void setCustomer(Integer customer) {
		this.customer = customer;
	}

	public Integer getNatureFollower() {
		return natureFollower;
	}

	public void setNatureFollower(Integer natureFollower) {
		this.natureFollower = natureFollower;
	}

}

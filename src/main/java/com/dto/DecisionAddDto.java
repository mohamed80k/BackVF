package com.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DecisionAddDto {

	@NotNull(message = "Le nom est nul !")
	@Size(min = 3, max = 100, message = "Le nom doit être comprise entre 3 et 100 caractères !")
	private String name;

	private Date createAt;

	private boolean done;

	@NotNull(message = "Le projet est nul !")
	private Integer project;

	@NotNull(message = "Le commercial est nul !")
	private Integer commercial;

	private Integer customer;

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

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
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

}

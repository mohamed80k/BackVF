package com.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EmailUpdateDto {

	@NotNull(message = "L'id est nul !")
	private Integer id;

	@NotNull(message = "Le type de l'émail est nul !")
	@Size(max = 100, message = "Le type de l'émail dépasse 50 caractères !")
	private String type;

	@NotNull(message = "Email est nul !")
	@Size(max = 2000, message = "L'émail dépasse 2000 caractères !")
	private String email1;

	@Size(max = 2000, message = "L'émail en copier dépasse 2000 caractères !")
	private String email2;

	@NotNull(message = "Le site est nul !")
	private Integer site;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public Integer getSite() {
		return site;
	}

	public void setSite(Integer site) {
		this.site = site;
	}

}

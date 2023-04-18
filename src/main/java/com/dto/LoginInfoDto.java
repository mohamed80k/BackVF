package com.dto;

import java.util.Date;

public class LoginInfoDto {

	private Integer id;

	private Date loginAt;

	public LoginInfoDto(Integer id, Date loginAt) {
		this.id = id;
		this.loginAt = loginAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getLoginAt() {
		return loginAt;
	}

	public void setLoginAt(Date loginAt) {
		this.loginAt = loginAt;
	}

}

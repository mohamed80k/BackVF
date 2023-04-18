package com.dto;

import java.util.Date;
import java.util.Set;

import com.entity.type.UserType;

public class UserInfoDto {

	private Integer id;

	private String username;

	private boolean enabled;

	private String description;

	private Date createAt;

	private UserType type;

	private Set<RoleInfoDto> roles;

	private CommercialInfoDto commercial;

	private LoginInfoDto loginLast;

	private UserInfoDto manager;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public Set<RoleInfoDto> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleInfoDto> roles) {
		this.roles = roles;
	}

	public CommercialInfoDto getCommercial() {
		return commercial;
	}

	public void setCommercial(CommercialInfoDto commercial) {
		this.commercial = commercial;
	}

	public LoginInfoDto getLoginLast() {
		return loginLast;
	}

	public void setLoginLast(LoginInfoDto loginLast) {
		this.loginLast = loginLast;
	}

	public UserInfoDto getManager() {
		return manager;
	}

	public void setManager(UserInfoDto manager) {
		this.manager = manager;
	}

}

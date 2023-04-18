package com.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.Table;

import com.entity.type.UserType;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "description")
	private String description;

	@Column(name = "create_at")
	private Date createAt;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private UserType type;

	@ManyToOne(fetch = FetchType.EAGER)
	private Commercial commercial;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	private Set<Role> roles;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Login> logins;

//	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private Set<AlertConfig> alertConfigs;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "manager")
	private User manager;

	public User() {
	}

	public User(Integer id, String username, String password, boolean enabled, String description, Date createAt,
			UserType type, Commercial commercial, Set<Role> roles, Set<Login> logins,
			User manager) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.description = description;
		this.createAt = createAt;
		this.type = type;
		this.commercial = commercial;
		this.roles = roles;
		this.logins = logins;

		this.manager = manager;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Commercial getCommercial() {
		return commercial;
	}

	public void setCommercial(Commercial commercial) {
		this.commercial = commercial;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Login> getLogins() {
		return logins;
	}

	public void setLogins(Set<Login> logins) {
		this.logins = logins;
	}

//	public Set<AlertConfig> getAlertConfigs() {
//		return alertConfigs;
//	}
//
//	public void setAlertConfigs(Set<AlertConfig> alertConfigs) {
//		this.alertConfigs = alertConfigs;
//	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", enabled=" + enabled
				+ ", description=" + description + ", createAt=" + createAt + ", type=" + type + ", commercial="
				+ commercial + ", roles=" + roles + ", logins=" + logins + ", alertConfigs="
				+ ", manager=" + manager + "]";
	}

}

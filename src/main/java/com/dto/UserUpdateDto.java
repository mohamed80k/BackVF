package com.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.entity.type.UserType;

public class UserUpdateDto {

	@NotNull(message = "L'id est null !")
	private Integer id;

	@NotNull(message = "Le nom d'utilisateur est null !")
	@Size(min = 3, max = 50, message = "Le nom d'utilisateur doit être comprise entre 3 et 50 caractères !")
	private String username;

	@Size(min = 4, message = "Le mot de passe inférieur de 4 caractères !")
	private String password;

	private boolean enabled;

	@Size(max = 50, message = "La description dépasse 50 caractères !")
	private String description;

	private Date createAt;

	private UserType type;

	@NotNull(message = "Les roles est null !")
	@NotEmpty(message = "Les roles est vide !")
	private List<Integer> roles;

	private Integer commercial;

	private Set<Integer> usersToManaged;

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

	public List<Integer> getRoles() {
		return roles;
	}

	public void setRoles(List<Integer> roles) {
		this.roles = roles;
	}

	public Integer getCommercial() {
		return commercial;
	}

	public void setCommercial(Integer commercial) {
		this.commercial = commercial;
	}

	public Set<Integer> getUsersToManaged() {
		return usersToManaged;
	}

	public void setUsersToManaged(Set<Integer> usersToManaged) {
		this.usersToManaged = usersToManaged;
	}

}

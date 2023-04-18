package com.dto;

import javax.validation.constraints.NotNull;

public class LoginDto {

	@NotNull(message = "Le nom d'utilisateur est nul !")
	private String username;

	@NotNull(message = "Le mot de passe d'utilisateur est nul !")
	private String password;

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

}

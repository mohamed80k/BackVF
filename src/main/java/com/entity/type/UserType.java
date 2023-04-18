package com.entity.type;

public enum UserType {

	admin("admin"), commercial("commercial"), commercial_manager("commercial_manager"), regional_manager("regional_manager"), commercial_director("commercial_director"), director_general("director_general");

	private String value;

	private UserType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

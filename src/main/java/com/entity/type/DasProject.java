package com.entity.type;

public enum DasProject {

	Amenagement("Amenagement"), Structure("Structure");

	private String value;

	private DasProject(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

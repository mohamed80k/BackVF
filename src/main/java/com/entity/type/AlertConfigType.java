package com.entity.type;

public enum AlertConfigType {

	follower_project("follower_project"), appointment("appointment"), returned_sale("returned_sale");

	private String value;

	private AlertConfigType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

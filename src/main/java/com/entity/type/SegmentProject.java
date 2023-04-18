package com.entity.type;

public enum SegmentProject {

	Entreprise("Entreprise"), Particulier("Particulier"), Revendeur("Revendeur");

	private String value;

	private SegmentProject(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

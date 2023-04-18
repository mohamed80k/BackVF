package com.entity.type;

public enum ImportType {

	articles("articles"), tiers("tiers"), states("states"), projects("projects"), lotissements("lotissements"), pointOfSales("pointOfSales");

	private String value;

	private ImportType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

package com.entity.type;

public enum PurchaseOrderType {

	Quote("Devis"), Command("Commande");

	private String value;

	private PurchaseOrderType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

package com.entity.type;

public enum StatusProject {

	Menara("Menara"), Concurrent("Concurrent"), Conclus("Conclus"), Partage("Partager"),En_Prospection("En_Prospection"), Non_Pris("Non_Pris");

	private String value;

	private StatusProject(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

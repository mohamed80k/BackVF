package com.entity.type;

public enum PointInterestType {

	MARKER(0), POLYGON(1), CERCLE(2), LINE(3), RECTANGLE(4);

	private int value;

	private PointInterestType(int state) {
		this.value = state;
	}

	public int getValue() {
		return value;
	}
}

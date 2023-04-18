package com.dto;

public class SocietyInfoDto {

	private Integer id;

	private String name;

//	private byte order;

	public SocietyInfoDto(Integer id, String menara) {
		this.id = id;
		this.name = menara;
	}

	public SocietyInfoDto() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public byte getOrder() {
//		return order;
//	}
//
//	public void setOrder(byte order) {
//		this.order = order;
//	}

}
package com.dto;

public class CustomerInfoDto extends TiersInfoDto {

	private TypeCustomerInfoDto typeCustomer;

	private String code;

	public TypeCustomerInfoDto getTypeCustomer() {
		return typeCustomer;
	}

	public void setTypeCustomer(TypeCustomerInfoDto typeCustomer) {
		this.typeCustomer = typeCustomer;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}

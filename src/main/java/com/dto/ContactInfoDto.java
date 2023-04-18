package com.dto;

public class ContactInfoDto {

	private Integer id;

	private String name;

	private ProfessionInfoDto profession;

	private String phone1;

	private String phone2;

	private String email;

	private CustomerInfoDto customer;

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

	public ProfessionInfoDto getProfession() {
		return profession;
	}

	public void setProfession(ProfessionInfoDto profession) {
		this.profession = profession;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CustomerInfoDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerInfoDto customer) {
		this.customer = customer;
	}

}

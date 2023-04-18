package com.dto;

import com.entity.Commercial;
import com.entity.Customer;
import com.entity.Project;

public class NumberOfVisitsReportDto {

	private Project project;

	private Commercial commercial;

	private Customer customer;

	private long numberOFVisits;

	public NumberOfVisitsReportDto(Project project, Commercial commercial, Customer customer, long numberOFVisits) {
		this.project = project;
		this.commercial = commercial;
		this.customer = customer;
		this.numberOFVisits = numberOFVisits;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Commercial getCommercial() {
		return commercial;
	}

	public void setCommercial(Commercial commercial) {
		this.commercial = commercial;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public long getNumberOFVisits() {
		return numberOFVisits;
	}

	public void setNumberOFVisits(long numberOFVisits) {
		this.numberOFVisits = numberOFVisits;
	}

}

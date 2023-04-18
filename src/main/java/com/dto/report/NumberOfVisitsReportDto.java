package com.dto.report;

import java.util.Date;

public interface NumberOfVisitsReportDto {

	public Integer getProjectId();

	public String getProjectName();

	public String getProjectTypeName();

	public String getProjectDescription();

	public String getProjectAddress();

	public double getProjectLatitude();

	public double getProjectLongitude();

	public Integer getCommercialId();

	public String getCommercialName();

	public long getNumberOFVisits();

	public Date getFollowedFist();

	public Date getFollowedLast();
	
	public String getCustomerName();
	
	public String getCustomerTypeName();

}
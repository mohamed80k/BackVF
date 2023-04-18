package com.dto.report;

import java.util.Date;

public interface ProjectStateDto {
	String getProjectName();
	String getType();
	String getAddress();
	double getLatitude();
	double getLongitude();
	String getDescription();
	String getClient();
	String getTypeClient();
	String getSegment();
	String getCommercial();
	double getCaBpe();
	double getCaMa();
	double getCaPl();
	double getCaPvDa();
	double getCaEstime();
	String getStatus();
	String getStateProject();
	String getPreviousStatus();
	Date getClosedAt();
	Date getCreatedAt();
	String getSite();
	String getLotissement();
	String getDas();
}

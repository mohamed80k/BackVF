package com.dto.report;

import com.entity.type.StatusProject;

public interface ProjectReportDto {

	Long getCount();
	StatusProject getStatus();
	String getProjectTypeName();
	Integer getCommercialId();
	String getCommercialName();
	Integer getMonth();
	Integer getYear();
	String getSiteId();
	String getSiteName();
}

package com.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.entity.type.ExportType;
import com.entity.type.StatusProject;

public class ExportDto {

	@NotNull(message = "Type est nul !")
	private ExportType exportType;

	private Date startDate;

	private Date endDate;
	
	private List<Integer> sites;
	
	private List<Integer> statesProject;
	
	private List<StatusProject> status;
	
	private boolean allSites;
	
	private boolean allStatus;

	private Integer eventId;

	public ExportType getExportType() {
		return exportType;
	}

	public void setExportType(ExportType exportType) {
		this.exportType = exportType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Integer> getSites() {
		return sites;
	}

	public void setSites(List<Integer> sites) {
		this.sites = sites;
	}

	public List<Integer> getStatesProject() {
		return statesProject;
	}

	public void setStatesProject(List<Integer> statesProject) {
		this.statesProject = statesProject;
	}

	public boolean isAllSites() {
		return allSites;
	}

	public void setAllSites(boolean allSites) {
		this.allSites = allSites;
	}

	public List<StatusProject> getStatus() {
		return status;
	}

	public void setStatus(List<StatusProject> status) {
		this.status = status;
	}

	public boolean isAllStatus() {
		return allStatus;
	}

	public void setAllStatus(boolean allStatus) {
		this.allStatus = allStatus;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
}

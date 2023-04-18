package com.service;

import java.util.List;

import com.dto.DateInterval;
import com.dto.PageDto;
import com.dto.ProjectAddDto;
import com.dto.ProjectInfoDto;
import com.dto.ProjectUpdateDto;
import com.dto.report.ProjectReportDto;
import com.entity.Project;

public interface ProjectService {

	public ProjectInfoDto add(ProjectAddDto projectAdd);

	public ProjectInfoDto update(ProjectUpdateDto projectUpdate);

	public ProjectInfoDto assignment(Integer id, Integer commercialId);

	public boolean delete(Integer id);

	public ProjectInfoDto getOne(Integer id);

	public PageDto<ProjectInfoDto> getPage(int page, int size, String search);

	public List<ProjectInfoDto> getList();

	public List<ProjectInfoDto> getListByCommercial(Integer commercialId);
	
	public List<ProjectReportDto> getStatistics(DateInterval dateInterval);
	
	public List<ProjectReportDto> getStatisticsByCommercial(DateInterval dateInterval);

	public boolean checkProjectInAffiliateCommercials(Project project);

	List<ProjectReportDto> getStatisticsBySite(DateInterval dateInterval);

	public Boolean deleteMultiple(List<Integer> projectsId);
}

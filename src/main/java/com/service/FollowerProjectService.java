package com.service;

import java.util.List;

import com.dto.DateInterval;
import com.dto.FollowerProjectAddDto;
import com.dto.FollowerProjectInfoDto;
import com.dto.FollowerProjectUpdateDto;
import com.dto.PageDto;
import com.dto.report.NumberOfVisitsReportDto;

public interface FollowerProjectService {

	public FollowerProjectInfoDto add(FollowerProjectAddDto followerProjectAdd);

	public FollowerProjectInfoDto update(FollowerProjectUpdateDto followerProjectUpdate);

	public boolean delete(Integer id);

	public FollowerProjectInfoDto getOne(Integer id);

	public PageDto<FollowerProjectInfoDto> getPage(int page, int size, String search);
	
	public List<FollowerProjectInfoDto> getListByCommercial(int commercialId);
	
	public List<FollowerProjectInfoDto> getListByPeriod(DateInterval dateInterval);
	
	public List<NumberOfVisitsReportDto> getStatistics(DateInterval dateInterval);
	
}
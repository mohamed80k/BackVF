package com.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.DateInterval;
import com.dto.FollowerProjectAddDto;
import com.dto.FollowerProjectInfoDto;
import com.dto.FollowerProjectUpdateDto;
import com.dto.PageDto;
import com.dto.report.NumberOfVisitsReportDto;
import com.service.FollowerProjectService;

@RestController
@RequestMapping(value = "/api/follower-projects")
@CrossOrigin
public class FollowerProjectController {

	@Autowired
	private FollowerProjectService followerProjectService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public FollowerProjectInfoDto getOne(@PathVariable("id") Integer id) {
		return followerProjectService.getOne(id);
	}

	@RequestMapping(value = "/list-by-commercial", method = RequestMethod.GET)
	public List<FollowerProjectInfoDto> getListByCommercial(@RequestParam(value="commercialId") int commercialId) {
		return followerProjectService.getListByCommercial(commercialId);
	}

	@RequestMapping(value = "/periods", method = RequestMethod.POST)
	public List<FollowerProjectInfoDto> getListByPeriod(@Valid @RequestBody DateInterval dateInterval) {
		return followerProjectService.getListByPeriod(dateInterval);
	}


	@RequestMapping(value = "/paginate", method = RequestMethod.GET)
	public PageDto<FollowerProjectInfoDto> getPage(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size, @RequestParam String search) {
		return followerProjectService.getPage(page, size, search);
	}

	@RequestMapping(method = RequestMethod.POST)
	public FollowerProjectInfoDto add(@Valid @RequestBody FollowerProjectAddDto followedProjectAdd) {
		return followerProjectService.add(followedProjectAdd);
	}

	@RequestMapping(value = "/statistics", method = RequestMethod.POST)
	public List<NumberOfVisitsReportDto> getStatistics(@Valid @RequestBody DateInterval dateInterval) {
		 return followerProjectService.getStatistics(dateInterval);
	}


	@RequestMapping(method = RequestMethod.PUT)
	public FollowerProjectInfoDto update(@Valid @RequestBody FollowerProjectUpdateDto followedProjectUpdate) {
		return followerProjectService.update(followedProjectUpdate);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return followerProjectService.delete(id);
	}

}

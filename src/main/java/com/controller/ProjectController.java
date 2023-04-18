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
import com.dto.PageDto;
import com.dto.ProjectAddDto;
import com.dto.ProjectInfoDto;
import com.dto.ProjectUpdateDto;
import com.dto.report.ProjectReportDto;
import com.service.ProjectService;

@RestController
@RequestMapping(value = "/api/projects")
@CrossOrigin
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ProjectInfoDto getOne(@PathVariable("id") Integer id) {
		return projectService.getOne(id);
	}

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public List<ProjectInfoDto> getList() {
		return projectService.getList();
	}

	@RequestMapping(value = "/paginate", method = RequestMethod.GET)
	public PageDto<ProjectInfoDto> getPage(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size, @RequestParam String search) {
		return projectService.getPage(page, size, search);
	}

	@RequestMapping(value = "/list-by-commercial", method = RequestMethod.GET)
	public List<ProjectInfoDto> getListByCommercial(@RequestParam(value = "commercialId") Integer commercialId) {
		return projectService.getListByCommercial(commercialId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ProjectInfoDto add(@Valid @RequestBody ProjectAddDto projectAdd) {
		return projectService.add(projectAdd);
	}

	@RequestMapping(value = "/statistics", method = RequestMethod.POST)
	public List<ProjectReportDto> getStatistics(@Valid @RequestBody DateInterval dateInterval) {
		return projectService.getStatistics(dateInterval);
	}

	@RequestMapping(value = "/commercials/statistics", method = RequestMethod.POST)
	public List<ProjectReportDto> getStatisticsByCommercial(@Valid @RequestBody DateInterval dateInterval) {
		return projectService.getStatisticsByCommercial(dateInterval);
	}

	@RequestMapping(value = "/sites/statistics", method = RequestMethod.POST)
	public List<ProjectReportDto> getStatisticsBySite(@Valid @RequestBody DateInterval dateInterval) {
		return projectService.getStatisticsBySite(dateInterval);
	}


	@RequestMapping(method = RequestMethod.PUT)
	public ProjectInfoDto update(@Valid @RequestBody ProjectUpdateDto projectUpdate) {
		return projectService.update(projectUpdate);
	}

	@RequestMapping(value = "/{id}/assignment/{commercialId}", method = RequestMethod.PUT)
	public ProjectInfoDto assignment(@PathVariable("id") Integer id, @PathVariable("commercialId") Integer commercialId) {
		return projectService.assignment(id, commercialId);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return projectService.delete(id);
	}

	@RequestMapping(value = "/multiple/{projectsId}", method = RequestMethod.DELETE)
	public Boolean deleteMultiple(@PathVariable("projectsId") List<Integer> projectsId) {
		return projectService.deleteMultiple(projectsId);
	}

}

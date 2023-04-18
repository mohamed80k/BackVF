package com.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dto.StateProjectAddDto;
import com.dto.StateProjectInfoDto;
import com.dto.StateProjectUpdateDto;
import com.service.StateProjectService;

@RestController
@RequestMapping(value = "/api/state/projects")
@CrossOrigin
public class StateProjectController {

	@Autowired
	private StateProjectService stateProjectService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public StateProjectInfoDto getOne(@PathVariable("id") Integer id) {
		return stateProjectService.getOne(id);
	}

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public List<StateProjectInfoDto> getList() {
		return stateProjectService.getList();
	}

	@RequestMapping(method = RequestMethod.POST)
	public StateProjectInfoDto add(@Valid @RequestBody StateProjectAddDto stateProjectAdd) {
		return stateProjectService.add(stateProjectAdd);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public StateProjectInfoDto update(@Valid @RequestBody StateProjectUpdateDto stateProjectUpdate) {
		return stateProjectService.update(stateProjectUpdate);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return stateProjectService.delete(id);
	}

}

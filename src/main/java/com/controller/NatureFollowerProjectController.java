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

import com.dto.NatureFollowerProjectAddDto;
import com.dto.NatureFollowerProjectInfoDto;
import com.dto.NatureFollowerProjectUpdateDto;
import com.service.NatureFollowerProjectService;

@RestController
@RequestMapping(value = "/api/nature-follower-projects")
@CrossOrigin
public class NatureFollowerProjectController {

	@Autowired
	private NatureFollowerProjectService natureFollowerService;

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public List<NatureFollowerProjectInfoDto> getList() {
		return natureFollowerService.getList();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public NatureFollowerProjectInfoDto getOne(@PathVariable("id") Integer id) {
		return natureFollowerService.getOne(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public NatureFollowerProjectInfoDto add(@Valid @RequestBody NatureFollowerProjectAddDto natureAdd) {
		return natureFollowerService.add(natureAdd);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public NatureFollowerProjectInfoDto update(@Valid @RequestBody NatureFollowerProjectUpdateDto natureUpdate) {
		return natureFollowerService.update(natureUpdate);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return natureFollowerService.delete(id);
	}
}

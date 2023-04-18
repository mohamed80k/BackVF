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

import com.dto.TypeProjectAddDto;
import com.dto.TypeProjectInfoDto;
import com.dto.TypeProjectUpdateDto;
import com.service.TypeProjectService;

@RestController
@RequestMapping(value = "/api/type/projects")
@CrossOrigin
public class TypeProjectController {

	@Autowired
	private TypeProjectService typeProjectService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public TypeProjectInfoDto getOne(@PathVariable("id") Integer id) {
		return typeProjectService.getOne(id);
	}

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public List<TypeProjectInfoDto> getList() {
		return typeProjectService.getList();
	}

	@RequestMapping(method = RequestMethod.POST)
	public TypeProjectInfoDto add(@Valid @RequestBody TypeProjectAddDto typeProjectAdd) {
		return typeProjectService.add(typeProjectAdd);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public TypeProjectInfoDto update(@Valid @RequestBody TypeProjectUpdateDto typeProjectUpdate) {
		return typeProjectService.update(typeProjectUpdate);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return typeProjectService.delete(id);
	}

}

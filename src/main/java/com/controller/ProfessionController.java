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

import com.dto.ProfessionAddDto;
import com.dto.ProfessionInfoDto;
import com.dto.ProfessionUpdateDto;
import com.service.ProfessionService;

@RestController
@RequestMapping(value = "/api/professions")
@CrossOrigin
public class ProfessionController {

	@Autowired
	private ProfessionService functionService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ProfessionInfoDto getOne(@PathVariable("id") Integer id) {
		return functionService.getOne(id);
	}

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public List<ProfessionInfoDto> getList() {
		return functionService.getList();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ProfessionInfoDto add(@Valid @RequestBody ProfessionAddDto professionAdd) {
		return functionService.add(professionAdd);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ProfessionInfoDto update(@Valid @RequestBody ProfessionUpdateDto professionUpdate) {
		return functionService.update(professionUpdate);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return functionService.delete(id);
	}

}

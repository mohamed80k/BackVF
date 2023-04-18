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

import com.dto.DecisionAddDto;
import com.dto.DecisionInfoDto;
import com.dto.DecisionUpdateDto;
import com.dto.PageDto;
import com.service.DecisionService;

@RestController
@RequestMapping(value = "/api/decisions")
@CrossOrigin
public class DecisionController {

	@Autowired
	private DecisionService decisionService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public DecisionInfoDto getOne(@PathVariable("id") Integer id) {
		return decisionService.getOne(id);
	}

	@RequestMapping(value = "/paginate", method = RequestMethod.GET)
	public PageDto<DecisionInfoDto> getPage(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size, @RequestParam String search) {
		return decisionService.getPage(page, size, search);
	}

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public List<DecisionInfoDto> getList() {
		return decisionService.getList();
	}

	@RequestMapping(value = "/list-by-commercial", method = RequestMethod.GET)
	public List<DecisionInfoDto> getListByCommercial(@RequestParam(value = "commercialId") int commercialId) {
		return decisionService.getListByCommercial(commercialId);
	}

	@RequestMapping(value = "/list/group-by-name", method = RequestMethod.GET)
	public List<DecisionInfoDto> getListGroupByName() {
		return decisionService.getListGroupByName();
	}

	@RequestMapping(method = RequestMethod.POST)
	public DecisionInfoDto add(@Valid @RequestBody DecisionAddDto decisionAdd) {
		return decisionService.add(decisionAdd);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public DecisionInfoDto update(@Valid @RequestBody DecisionUpdateDto decisionUpdate) {
		return decisionService.update(decisionUpdate);
	}

	@RequestMapping(value = "{id}/edit-status/{done}", method = RequestMethod.PUT)
	public DecisionInfoDto update(@PathVariable("id") Integer id, @PathVariable("done") boolean done) {
		return decisionService.editStatusDecision(id, done);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return decisionService.delete(id);
	}

}

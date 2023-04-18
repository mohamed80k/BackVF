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

import com.dto.TypeTiersAddDto;
import com.dto.TypeTiersInfoDto;
import com.dto.TypeTiersUpdateDto;
import com.service.TypeTiersService;

@RestController
@RequestMapping(value = "/api/type/tiers")
@CrossOrigin
public class TypeTiersController {

	@Autowired
	private TypeTiersService typeTiersService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public TypeTiersInfoDto getOne(@PathVariable("id") Integer id) {
		return typeTiersService.getOne(id);
	}

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public List<TypeTiersInfoDto> getList() {
		return typeTiersService.getList();
	}

	@RequestMapping(method = RequestMethod.POST)
	public TypeTiersInfoDto add(@Valid @RequestBody TypeTiersAddDto typeTierAdd) {
		return typeTiersService.add(typeTierAdd);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public TypeTiersInfoDto update(@Valid @RequestBody TypeTiersUpdateDto typeTierUpdate) {
		return typeTiersService.update(typeTierUpdate);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return typeTiersService.delete(id);
	}

}

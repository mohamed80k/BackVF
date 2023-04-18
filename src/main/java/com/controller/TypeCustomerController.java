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

import com.dto.TypeCustomerAddDto;
import com.dto.TypeCustomerInfoDto;
import com.dto.TypeCustomerUpdateDto;
import com.service.TypeCustomerService;

@RestController
@RequestMapping(value = "/api/type/customers")
@CrossOrigin
public class TypeCustomerController {

	@Autowired
	private TypeCustomerService typeCustomerService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public TypeCustomerInfoDto getOne(@PathVariable("id") Integer id) {
		return typeCustomerService.getOne(id);
	}

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public List<TypeCustomerInfoDto> getList() {
		return typeCustomerService.getList();
	}

	@RequestMapping(method = RequestMethod.POST)
	public TypeCustomerInfoDto add(@Valid @RequestBody TypeCustomerAddDto typeCustomerAdd) {
		return typeCustomerService.add(typeCustomerAdd);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public TypeCustomerInfoDto update(@Valid @RequestBody TypeCustomerUpdateDto typeCustomerUpdate) {
		return typeCustomerService.update(typeCustomerUpdate);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return typeCustomerService.delete(id);
	}

}

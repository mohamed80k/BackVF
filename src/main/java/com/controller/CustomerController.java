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

import com.dto.CustomerAddDto;
import com.dto.CustomerInfoDto;
import com.dto.CustomerUpdateDto;
import com.dto.PageDto;
import com.service.CustomerService;

@RestController
@RequestMapping(value = "/api/customers")
@CrossOrigin
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public CustomerInfoDto getOne(@PathVariable("id") Integer id) {
		return customerService.getOne(id);
	}

	@RequestMapping(value = "/paginate", method = RequestMethod.GET)
	public PageDto<CustomerInfoDto> getPage(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		return customerService.getPage(page, size);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<CustomerInfoDto> getList() {
		return customerService.getList();
	}

	@RequestMapping(value = "/list/revendeurs", method = RequestMethod.GET)
	public List<CustomerInfoDto> getListRevendeurs() {
		return customerService.getListRevendeurs();
	}


	@RequestMapping(value = "/list-by-commercial", method = RequestMethod.GET)
	public List<CustomerInfoDto> getListByCommercial(@RequestParam("commercialId") Integer commercialId) {
		return customerService.getListByCommercial(commercialId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public CustomerInfoDto add(@Valid @RequestBody CustomerAddDto customerAdd) {
		return customerService.add(customerAdd);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public CustomerInfoDto update(@Valid @RequestBody CustomerUpdateDto customerUpdate) {
		return customerService.update(customerUpdate);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return customerService.delete(id);
	}

}

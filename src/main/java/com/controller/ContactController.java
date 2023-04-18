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

import com.dto.ContactAddDto;
import com.dto.ContactInfoDto;
import com.dto.ContactUpdateDto;
import com.dto.PageDto;
import com.service.ContactService;

@RestController
@RequestMapping(value = "/api/contacts")
@CrossOrigin
public class ContactController {

	@Autowired
	private ContactService contactService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ContactInfoDto getOne(@PathVariable("id") Integer id) {
		return contactService.getOne(id);
	}

	@RequestMapping(value = "/paginate", method = RequestMethod.GET)
	public PageDto<ContactInfoDto> getPage(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size,@RequestParam String search) {
		return contactService.getPage(page, size, search);
	}

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public List<ContactInfoDto> getList() {
		return contactService.getList();
	}

	@RequestMapping(value = "/list-by-customer", method = RequestMethod.GET)
	public List<ContactInfoDto> getListByCustomer(@RequestParam("customerId") int customerId) {
		return contactService.getListByCustomer(customerId);
	}

	@RequestMapping(value = "/list-by-commercial", method = RequestMethod.GET)
	public List<ContactInfoDto> getListByCommercial(@RequestParam(value = "commercialId") Integer commercialId) {
		return contactService.getListByCommercial(commercialId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ContactInfoDto add(@Valid @RequestBody ContactAddDto contactAdd) {
		return contactService.add(contactAdd);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ContactInfoDto update(@Valid @RequestBody ContactUpdateDto contactUpdate) {
		return contactService.update(contactUpdate);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return contactService.delete(id);
	}

}

package com.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.EmailAddDto;
import com.dto.EmailInfoDto;
import com.dto.EmailUpdateDto;
import com.dto.PageDto;
import com.service.EmailService;

@RestController
@RequestMapping(value = "/api/emails")
@CrossOrigin
public class EmailController {

	@Autowired
	private EmailService emailService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public EmailInfoDto getOne(@PathVariable("id") Integer id) {
		return emailService.getOne(id);
	}

	@RequestMapping(value = "/paginate-by-society", method = RequestMethod.GET)
	public PageDto<EmailInfoDto> getPage(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size,
			@RequestParam("societyId") Integer societyId) {
		return emailService.getPage(page, size, societyId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public EmailInfoDto add(@Valid @RequestBody EmailAddDto emailAdd) {
		return emailService.add(emailAdd);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public EmailInfoDto update(@Valid @RequestBody EmailUpdateDto emailUpdate) {
		return emailService.update(emailUpdate);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return emailService.delete(id);
	}

}

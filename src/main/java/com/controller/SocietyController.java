package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dto.SocietyInfoDto;
import com.service.SocietyService;

@RestController
@RequestMapping(value = "/api/societies")
@CrossOrigin
public class SocietyController {

	@Autowired
	private SocietyService societyService;

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public List<SocietyInfoDto> getList() {
		return societyService.getList();
	}

}

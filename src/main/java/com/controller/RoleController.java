package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dto.RoleInfoDto;
import com.service.RoleService;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin
public class RoleController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public List<RoleInfoDto> getList() {
		return roleService.getList();
	}
}

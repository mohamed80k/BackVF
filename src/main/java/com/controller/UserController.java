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

import com.dto.LoginDto;
import com.dto.UserAddDto;
import com.dto.UserInfoDto;
import com.dto.UserUpdateDto;
import com.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public String login(@Valid @RequestBody LoginDto loginDto) {
		return userService.signin(loginDto);
	}

	@RequestMapping(value = "/whoami", method = RequestMethod.GET)
	public UserInfoDto getUserCurrentUser() {
		return userService.getCurrentUser();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public UserInfoDto getOne(@PathVariable("id") Integer id) {
		return userService.getOne(id);
	}

	@RequestMapping(value = "/userName/{userName}", method = RequestMethod.GET)
	public UserInfoDto getOneByName(@PathVariable("userName") String userName) {
		return userService.getOne(userName);
	}

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public List<UserInfoDto> getList() {
		return userService.getList();
	}

	@RequestMapping(method = RequestMethod.POST)
	public UserInfoDto add(@Valid @RequestBody UserAddDto userAdd) {
		return userService.add(userAdd);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public UserInfoDto update(@Valid @RequestBody UserUpdateDto UserUpdate) {
		return userService.update(UserUpdate);
	}

	@RequestMapping(value = "/{id}/edit-state/{isEnabled}", method = RequestMethod.PUT)
	public UserInfoDto editState(@PathVariable("id") Integer id, @PathVariable("isEnabled") boolean isEnabled) {
		return userService.editState(id, isEnabled);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return userService.delete(id);
	}

}
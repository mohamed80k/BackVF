package com.service;

import java.util.List;

import com.dto.LoginDto;
import com.dto.UserAddDto;
import com.dto.UserInfoDto;
import com.dto.UserUpdateDto;
import com.entity.User;

public interface UserService {

	public UserInfoDto add(UserAddDto userAdd);

	public UserInfoDto update(UserUpdateDto userUpdate);

	public UserInfoDto editState(Integer id, boolean isEnabled);

	public boolean delete(Integer id);

	public UserInfoDto getOne(Integer id);

	public UserInfoDto getOne(String userName);

	public List<UserInfoDto> getList();

	public String signin(LoginDto loginDto);

	public UserInfoDto getCurrentUser();

	public User whoami();

}

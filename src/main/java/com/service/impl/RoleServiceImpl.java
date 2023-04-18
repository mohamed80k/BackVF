package com.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.RoleInfoDto;
import com.repository.RoleRepository;
import com.service.RoleService;
import com.util.Utils;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<RoleInfoDto> getList() {
		return Utils.map(modelMapper, roleRepository.findAll(), RoleInfoDto.class);
	}

}

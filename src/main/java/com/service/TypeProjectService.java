package com.service;

import java.util.List;

import com.dto.TypeProjectAddDto;
import com.dto.TypeProjectInfoDto;
import com.dto.TypeProjectUpdateDto;

public interface TypeProjectService {

	public TypeProjectInfoDto add(TypeProjectAddDto typeProjectAdd);

	public TypeProjectInfoDto update(TypeProjectUpdateDto typeProjectUpdate);

	public boolean delete(Integer id);
	
	public TypeProjectInfoDto getOne(Integer id);

	public List<TypeProjectInfoDto> getList();
}

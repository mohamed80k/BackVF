package com.service;

import java.util.List;

import com.dto.StateProjectAddDto;
import com.dto.StateProjectInfoDto;
import com.dto.StateProjectUpdateDto;

public interface StateProjectService {

	public StateProjectInfoDto add(StateProjectAddDto stateProjectAdd);

	public StateProjectInfoDto update(StateProjectUpdateDto stateProjectUpdate);

	public boolean delete(Integer id);

	public StateProjectInfoDto getOne(Integer id);
	
	public List<StateProjectInfoDto> getList();
}

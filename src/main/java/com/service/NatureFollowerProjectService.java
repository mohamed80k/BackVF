package com.service;

import java.util.List;

import com.dto.NatureFollowerProjectAddDto;
import com.dto.NatureFollowerProjectInfoDto;
import com.dto.NatureFollowerProjectUpdateDto;

public interface NatureFollowerProjectService {

	public NatureFollowerProjectInfoDto add(NatureFollowerProjectAddDto natureAdd);

	public NatureFollowerProjectInfoDto update(NatureFollowerProjectUpdateDto natureUpdate);

	public boolean delete(Integer id);

	public NatureFollowerProjectInfoDto getOne(Integer id);

	public List<NatureFollowerProjectInfoDto> getList();
}

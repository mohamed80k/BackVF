package com.service;

import java.util.List;

import com.dto.ProfessionAddDto;
import com.dto.ProfessionInfoDto;
import com.dto.ProfessionUpdateDto;

public interface ProfessionService {

	public ProfessionInfoDto add(ProfessionAddDto professionAdd);

	public ProfessionInfoDto update(ProfessionUpdateDto professionUpdate);

	public boolean delete(Integer id);

	public ProfessionInfoDto getOne(Integer id);

	public List<ProfessionInfoDto> getList();
}

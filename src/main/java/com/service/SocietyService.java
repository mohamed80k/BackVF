package com.service;

import java.util.List;

import com.dto.SocietyInfoDto;
import com.entity.Society;

public interface SocietyService {
	public SocietyInfoDto getOne(Integer id);
	public SocietyInfoDto AddSociete(SocietyInfoDto Society);
	public SocietyInfoDto UpdateSociete(SocietyInfoDto Society);
	public Boolean DeleteSociete(Integer id);
	public List<SocietyInfoDto> getList();
}

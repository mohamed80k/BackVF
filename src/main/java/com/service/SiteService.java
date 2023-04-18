package com.service;

import java.util.List;

import com.dto.SiteAddDto;
import com.dto.SiteInfoDto;
import com.dto.SiteUpdateDto;

public interface SiteService {

	public SiteInfoDto add(SiteAddDto siteAdd);

	public SiteInfoDto update(SiteUpdateDto siteUpdate);

	public boolean delete(Integer id);

	public SiteInfoDto getOne(Integer id);

	public List<SiteInfoDto> getList();

	public List<SiteInfoDto> getListBySociety(Integer societyId);
	
	public List<SiteInfoDto> getListBySocietyAndType(Integer societyId, String type);
	
	public List<SiteInfoDto> getListBySocietyAndTypeAndCommercial(Integer societyId, String type, int commercialId);

}

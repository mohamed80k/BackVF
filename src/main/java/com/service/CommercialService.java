package com.service;

import java.util.List;

import com.dto.CommercialAddDto;
import com.dto.CommercialInfoDto;
import com.dto.CommercialUpdateDto;
import com.dto.PageDto;
import com.entity.Commercial;

public interface CommercialService {

	public CommercialInfoDto add(CommercialAddDto commercialAdd);

	public CommercialInfoDto update(CommercialUpdateDto commercialUpdate);

	public boolean delete(Integer id);

	public CommercialInfoDto getOne(Integer id);

	public PageDto<CommercialInfoDto> getPageBySociety(int page, int size, Integer societyId, String search);

	public List<CommercialInfoDto> getList();

	public List<CommercialInfoDto> getListBySociety(Integer societyId);
	
	public List<Commercial> getAffiliateCommercials();
	
	public boolean checkCommercialInAffiliateCommercials(Commercial commercial);

}

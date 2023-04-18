package com.service;

import java.util.List;

import com.dto.DateInterval;
import com.dto.PageDto;
import com.dto.TiersAddDto;
import com.dto.TiersInfoDto;
import com.dto.TiersUpdateDto;
import com.dto.report.StatisticInfoDto;
import com.entity.Tiers;

public interface TiersService {

	public TiersInfoDto add(TiersAddDto tierAdd);

	public TiersInfoDto update(TiersUpdateDto tierUpdate);
	
	public TiersInfoDto assignment(Integer id, Integer commercialId);

	public boolean delete(Integer id);

	public TiersInfoDto getOne(Integer id);

	public PageDto<TiersInfoDto> getPage(int page, int size, String search);

	public List<TiersInfoDto> getList();

	public List<TiersInfoDto> getListByTypes(List<String> typesTier);
	
	public List<TiersInfoDto> getListByCommercial(int commercialId);
	
	public List<TiersInfoDto> getListByTypesAndCommercial(List<String> typesTier, Integer commercialId);
	
	public List<StatisticInfoDto> getStatistics(DateInterval dateInterval);
	
	public boolean checkTiersInAffiliateCommercials(Tiers tiers);
}

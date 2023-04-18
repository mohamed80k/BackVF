package com.service;

import java.util.List;

import com.dto.TypeTiersAddDto;
import com.dto.TypeTiersInfoDto;
import com.dto.TypeTiersUpdateDto;

public interface TypeTiersService {

	public TypeTiersInfoDto add(TypeTiersAddDto typeTiersAdd);

	public TypeTiersInfoDto update(TypeTiersUpdateDto typeTiersUpdate);

	public boolean delete(Integer id);

	public TypeTiersInfoDto getOne(Integer id);
	
	public List<TypeTiersInfoDto> getList();
}

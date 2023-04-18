package com.service;

import java.util.List;

import com.dto.LotissementAddDto;
import com.dto.LotissementInfoDto;
import com.dto.LotissementUpdateDto;
import com.dto.PageDto;

public interface LotissementService {

	public LotissementInfoDto add(LotissementAddDto LotissementAdd);

	public LotissementInfoDto update(LotissementUpdateDto LotissementUpdate);

	public boolean delete(Integer id);

	public LotissementInfoDto getOne(Integer id);

	public PageDto<LotissementInfoDto> getPage(int page, int size, String search);

	public List<LotissementInfoDto> getList();

}

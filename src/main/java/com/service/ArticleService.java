package com.service;

import java.util.List;

import com.dto.ArticleAddDto;
import com.dto.ArticleInfoDto;
import com.dto.ArticleUpdateDto;
import com.dto.PageDto;

public interface ArticleService {

	public ArticleInfoDto add(ArticleAddDto articleAdd);

	public ArticleInfoDto update(ArticleUpdateDto articleUpdate);

	public boolean delete(Integer id);

	public ArticleInfoDto getOne(Integer id);

	public PageDto<ArticleInfoDto> getPage(int page, int size, Integer societyId);
	
	public List<ArticleInfoDto> getListBySociety(Integer societyId);
	
	public List<ArticleInfoDto> getListByCategory(Integer categoryId);
	
}

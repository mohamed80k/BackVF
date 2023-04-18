package com.service;

import java.util.List;

import com.dto.CategoryAddDto;
import com.dto.CategoryInfoDto;
import com.dto.CategoryUpdateDto;

public interface CategoryService {

	public CategoryInfoDto add(CategoryAddDto categoryAdd);

	public CategoryInfoDto update(CategoryUpdateDto categoryUpdate);

	public boolean delete(Integer id);

	public CategoryInfoDto getOne(Integer id);

	public List<CategoryInfoDto> getList();
	
	public List<CategoryInfoDto> getListBySociety(Integer societyId);
}

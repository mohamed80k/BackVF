package com.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.CategoryAddDto;
import com.dto.CategoryInfoDto;
import com.dto.CategoryUpdateDto;
import com.service.CategoryService;

@RestController
@RequestMapping(value = "/api/categories")
@CrossOrigin
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public CategoryInfoDto getOne(@PathVariable("id") Integer id) {
		return categoryService.getOne(id);
	}

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public List<CategoryInfoDto> getList() {
		return categoryService.getList();
	}

	@RequestMapping(value = "/list-by-society", method = RequestMethod.GET)
	public List<CategoryInfoDto> getListBySociety(@RequestParam("societyId") int societyId) {
		return categoryService.getListBySociety(societyId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public CategoryInfoDto add(@Valid @RequestBody CategoryAddDto categoryAdd) {
		return categoryService.add(categoryAdd);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public CategoryInfoDto update(@Valid @RequestBody CategoryUpdateDto categoryUpdate) {
		return categoryService.update(categoryUpdate);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return categoryService.delete(id);
	}

}

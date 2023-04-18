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

import com.dto.ArticleAddDto;
import com.dto.ArticleInfoDto;
import com.dto.ArticleUpdateDto;
import com.dto.PageDto;
import com.service.ArticleService;

@RestController
@RequestMapping(value = "/api/articles")
@CrossOrigin
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ArticleInfoDto getOne(@PathVariable("id") Integer id) {
		return articleService.getOne(id);
	}

	@RequestMapping(value = "/paginate-by-society", method = RequestMethod.GET)
	public PageDto<ArticleInfoDto> getPage(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size,
			@RequestParam("societyId") Integer societyId) {
		return articleService.getPage(page, size, societyId);
	}

	@RequestMapping(value = "list-by-society", method = RequestMethod.GET)
	public List<ArticleInfoDto> getListBySociety(@RequestParam("societyId") Integer societyId) {
		return articleService.getListBySociety(societyId);
	}

	@RequestMapping(value = "list-by-category", method = RequestMethod.GET)
	public List<ArticleInfoDto> getListByCategory(@RequestParam("categoryId") Integer categoryId) {
		return articleService.getListByCategory(categoryId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ArticleInfoDto add(@Valid @RequestBody ArticleAddDto ArticleAdd) {
		return articleService.add(ArticleAdd);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ArticleInfoDto update(@Valid @RequestBody ArticleUpdateDto ArticleUpdate) {
		return articleService.update(ArticleUpdate);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return articleService.delete(id);
	}

}

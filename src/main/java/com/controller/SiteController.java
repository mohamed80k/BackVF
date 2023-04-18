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

import com.dto.SiteAddDto;
import com.dto.SiteInfoDto;
import com.dto.SiteUpdateDto;
import com.service.SiteService;

@RestController
@RequestMapping(value = "/api/sites")
@CrossOrigin
public class SiteController {

	@Autowired
	private SiteService siteService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public SiteInfoDto getOne(@PathVariable("id") Integer id) {
		return siteService.getOne(id);
	}

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public List<SiteInfoDto> getList() {
		return siteService.getList();
	}

	@RequestMapping(value = "/list-by-society", method = RequestMethod.GET)
	public List<SiteInfoDto> getListBySociety(@RequestParam("societyId") Integer societyId) {
		return siteService.getListBySociety(societyId);
	}

	@RequestMapping(value = "/list-by-society-and-type", method = RequestMethod.GET)
	public List<SiteInfoDto> getListByCommercial(@RequestParam("societyId") Integer societyId,
			@RequestParam("type") String type) {
		return siteService.getListBySocietyAndType(societyId, type);
	}

	@RequestMapping(value = "/list-by-society-and-type-and-commercial", method = RequestMethod.GET)
	public List<SiteInfoDto> getListByCommercial(@RequestParam("societyId") Integer societyId,
			@RequestParam("type") String type, @RequestParam(value = "commercialId") int commercialId) {
		return siteService.getListBySocietyAndTypeAndCommercial(societyId, type, commercialId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public SiteInfoDto add(@Valid @RequestBody SiteAddDto siteAdd) {
		return siteService.add(siteAdd);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public SiteInfoDto update(@Valid @RequestBody SiteUpdateDto siteUpdate) {
		return siteService.update(siteUpdate);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return siteService.delete(id);
	}

}
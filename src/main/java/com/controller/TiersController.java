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

import com.dto.DateInterval;
import com.dto.PageDto;
import com.dto.TiersAddDto;
import com.dto.TiersInfoDto;
import com.dto.TiersUpdateDto;
import com.dto.report.StatisticInfoDto;
import com.service.TiersService;

@RestController
@RequestMapping(value = "/api/tiers")
@CrossOrigin
public class TiersController {

	@Autowired
	private TiersService tiersService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public TiersInfoDto getOne(@PathVariable("id") Integer id) {
		return tiersService.getOne(id);
	}

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public List<TiersInfoDto> getList() {
		return tiersService.getList();
	}

	@RequestMapping(value = "/paginate", method = RequestMethod.GET)
	public PageDto<TiersInfoDto> getPage(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size, @RequestParam String search) {
		return tiersService.getPage(page, size, search);
	}

	@RequestMapping(value = "/list-by-types", method = RequestMethod.GET)
	public List<TiersInfoDto> getListByTypes(@RequestParam(value = "types") List<String> typesTier) {
		return tiersService.getListByTypes(typesTier);
	}


	@RequestMapping(value = "/list-by-commercial", method = RequestMethod.GET)
	public List<TiersInfoDto> getListByCommercial(@RequestParam(value="commercialId") Integer commercialId) {
		return tiersService.getListByCommercial(commercialId);
	}

	@RequestMapping(value = "/list-by-types-and-commercial", method = RequestMethod.GET)
	public List<TiersInfoDto> getListByTypesAndCommercial(@RequestParam(value = "types") List<String> typesTier, @RequestParam(value="commercialId") Integer commercialId) {
		return tiersService.getListByTypesAndCommercial(typesTier, commercialId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public TiersInfoDto add(@Valid @RequestBody TiersAddDto tierAdd) {
		return tiersService.add(tierAdd);
	}

	@RequestMapping(value = "/statistics", method = RequestMethod.POST)
	public List<StatisticInfoDto> getStatistics(@Valid @RequestBody DateInterval dateInterval) {
		return tiersService.getStatistics(dateInterval);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public TiersInfoDto update(@Valid @RequestBody TiersUpdateDto tierUpdate) {
		return tiersService.update(tierUpdate);
	}

	@RequestMapping(value = "/{id}/assignment/{commercialId}", method = RequestMethod.PUT)
	public TiersInfoDto assignment(@PathVariable("id") Integer id, @PathVariable("commercialId") Integer commercialId) {
		return tiersService.assignment(id, commercialId);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return tiersService.delete(id);
	}

}

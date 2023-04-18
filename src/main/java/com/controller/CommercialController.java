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

import com.dto.CommercialAddDto;
import com.dto.CommercialInfoDto;
import com.dto.CommercialUpdateDto;
import com.dto.PageDto;
import com.service.CommercialService;

@RestController
@RequestMapping(value = "/api/commercials")
@CrossOrigin
public class CommercialController {

	@Autowired
	private CommercialService commercialService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public CommercialInfoDto getOne(@PathVariable("id") Integer id) {
		return commercialService.getOne(id);
	}

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public List<CommercialInfoDto> getList() {
		return commercialService.getList();
	}

	@RequestMapping(value = { "", "/list-by-society" }, method = RequestMethod.GET)
	public List<CommercialInfoDto> getListBySociety(@RequestParam("societyId") Integer societyId) {
		return commercialService.getListBySociety(societyId);
	}

	@RequestMapping(value = "/paginate-by-society", method = RequestMethod.GET)
	public PageDto<CommercialInfoDto> getPageBySociety(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size,
			@RequestParam("societyId") Integer societyId, @RequestParam String search) {
		return commercialService.getPageBySociety(page, size, societyId, search);
	}

	@RequestMapping(method = RequestMethod.POST)
	public CommercialInfoDto add(@Valid @RequestBody CommercialAddDto commercialAdd) {
		return commercialService.add(commercialAdd);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public CommercialInfoDto update(@Valid @RequestBody CommercialUpdateDto commercialUpdate) {
		return commercialService.update(commercialUpdate);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return commercialService.delete(id);
	}

}

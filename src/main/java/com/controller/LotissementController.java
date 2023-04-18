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

import com.dto.LotissementAddDto;
import com.dto.LotissementInfoDto;
import com.dto.LotissementUpdateDto;
import com.dto.PageDto;
import com.service.LotissementService;

@RestController
@RequestMapping(value = "/api/lotissements")
@CrossOrigin
public class LotissementController {

	@Autowired
	private LotissementService lotissementService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public LotissementInfoDto getOne(@PathVariable("id") Integer id) {
		return lotissementService.getOne(id);
	}

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public List<LotissementInfoDto> getList() {
		return lotissementService.getList();
	}

	@RequestMapping(value = "/paginate", method = RequestMethod.GET)
	public PageDto<LotissementInfoDto> getPage(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size, @RequestParam String search) {
		return lotissementService.getPage(page, size, search);
	}

	@RequestMapping(method = RequestMethod.POST)
	public LotissementInfoDto add(@Valid @RequestBody LotissementAddDto lotissementAdd) {
		return lotissementService.add(lotissementAdd);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public LotissementInfoDto update(@Valid @RequestBody LotissementUpdateDto lotissementUpdate) {
		return lotissementService.update(lotissementUpdate);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return lotissementService.delete(id);
	}

}

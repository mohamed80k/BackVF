package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dto.PoiDto;
import com.entity.PointInterest;
import com.entity.type.PointInterestType;
import com.service.PointInterestService;

@RestController
@RequestMapping("/api/pointInterests")
@CrossOrigin
public class PointInterestController {

	@Autowired
	private PointInterestService pointInterestService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<PoiDto> getListPointInterests() {
		return pointInterestService.getListPointInterests();
	}


	@RequestMapping(value = "type/{type}", method = RequestMethod.GET)
	public List<PoiDto> getListPointInterestsByType(@PathVariable("type") PointInterestType type) {
		return pointInterestService.getListPoiByType(type);
	}

	@RequestMapping(value = "{idPointInterest}", method = RequestMethod.GET)
	public PoiDto getPointInterestById(@PathVariable("idPointInterest") Long idPointInterest) {
		return pointInterestService.getPointInterestById(idPointInterest);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public PoiDto addPointInterest(@RequestBody PointInterest pointInterest) {
		return pointInterestService.addPointInterest(pointInterest);
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	public PoiDto updatePointInterest(@RequestBody PointInterest pointInterest) {
		return pointInterestService.updatePointInterest(pointInterest);
	}

	@RequestMapping(value = "{idPointInterest}", method = RequestMethod.DELETE)
	public Boolean deletePointInterest(@PathVariable("idPointInterest") Long idPointInterest) {
		return pointInterestService.deletePointInterest(idPointInterest);
	}

}

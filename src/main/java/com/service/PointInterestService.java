package com.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dto.PoiDto;
import com.entity.PointInterest;
import com.entity.type.PointInterestType;

public interface PointInterestService {

	public List<PoiDto> getListPointInterests();

	public PoiDto getPointInterestById(Long idPointInterest);

	public PoiDto addPointInterest(PointInterest pointInterest);

	public PoiDto updatePointInterest(PointInterest pointInterest);

	public Boolean deletePointInterest(Long idPointInterest);

	public List<PoiDto> getListPoiByType(PointInterestType type);


}

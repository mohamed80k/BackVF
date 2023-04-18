package com.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.PoiDto;
import com.entity.PointInterest;
import com.entity.type.PointInterestType;
import com.repository.PointInteretRepository;
import com.service.PointInterestService;
import com.util.Utils;

@Service
public class PointInterestServiceImpl implements PointInterestService {

	@Autowired
	PointInteretRepository pointInteretRepository;
	ModelMapper modelMapper = new ModelMapper();


	@Override
	public PoiDto addPointInterest(PointInterest pointInterest) {
		if(pointInterest.getAddress() != null && !pointInterest.getAddress().isEmpty() && !pointInterest.getAddress().equals("")) {
			pointInterest.setAddress(pointInterest.getAddress().replaceAll("\\?", ""));
		}
		return modelMapper.map(this.pointInteretRepository.save(pointInterest), PoiDto.class);
	}

	@Override
	public PoiDto updatePointInterest(PointInterest pointInterest) {
		try {
			if(pointInterest.getAddress() != null && !pointInterest.getAddress().isEmpty() && !pointInterest.getAddress().equals("")) {
				pointInterest.setAddress(pointInterest.getAddress().replaceAll("\\?", ""));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return modelMapper.map(this.pointInteretRepository.saveAndFlush(pointInterest), PoiDto.class);
	}

	@Override
	public Boolean deletePointInterest(Long idPointInterest) {
		try {
			this.pointInteretRepository.deleteById(idPointInterest);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<PoiDto> getListPointInterests() {
		return Utils.map(modelMapper, this.pointInteretRepository.findAll(), PoiDto.class);
	}

	@Override
	public PoiDto getPointInterestById(Long idPointInterest) {
		return modelMapper.map(this.pointInteretRepository.getOne(idPointInterest), PoiDto.class) ;
	}

	@Override
	public List<PoiDto> getListPoiByType(PointInterestType type) {
		return Utils.map(modelMapper, this.pointInteretRepository.findByType(type), PoiDto.class);

	}

}

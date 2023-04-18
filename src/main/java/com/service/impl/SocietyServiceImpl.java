package com.service.impl;

import java.util.List;

import com.entity.Society;
import com.exception.ResourceAlreadyExistException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.SocietyInfoDto;
import com.repository.SocietyRepository;
import com.service.SocietyService;
import com.util.Utils;

@Service
public class SocietyServiceImpl implements SocietyService {

	@Autowired
	private SocietyRepository societyRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	public SocietyInfoDto getOne(Integer id) {
		return null;
	}

	@Override
	public SocietyInfoDto AddSociete(SocietyInfoDto Society) {
		if(societyRepository.existsByName(Society.getName())){
			throw new ResourceAlreadyExistException("La Societe est déja existe");
		}
		Society s = modelMapper.map(Society,Society.class);

		societyRepository.save(s);
		return Society ;
	}

	@Override
	public SocietyInfoDto UpdateSociete(SocietyInfoDto Society) {
		return null;
	}

	@Override
	public Boolean DeleteSociete(Integer id) {
		return null;
	}

	@Override
	public List<SocietyInfoDto> getList() {
		return Utils.map(modelMapper, societyRepository.findAll(), SocietyInfoDto.class);
	}

}

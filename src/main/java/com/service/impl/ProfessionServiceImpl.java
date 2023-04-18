package com.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.ProfessionAddDto;
import com.dto.ProfessionInfoDto;
import com.dto.ProfessionUpdateDto;
import com.entity.Profession;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceNotFoundException;
import com.repository.ContactRepository;
import com.repository.ProfessionRepository;
import com.service.ProfessionService;
import com.util.Utils;

@Service
public class ProfessionServiceImpl implements ProfessionService {

	@Autowired
	ProfessionRepository professionRepository;

	@Autowired
	ContactRepository contactRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public ProfessionInfoDto add(ProfessionAddDto professionAdd) {
		/**
		 * Vérification de profession
		 */
		if (professionRepository.existsByName(professionAdd.getName()))
			throw new ResourceAlreadyExistException("Profession existe déja !");

		/**
		 * Ajouter
		 */
		Profession profession = modelMapper.map(professionAdd, Profession.class);
		professionRepository.save(profession);

		/**
		 * Retourne data
		 */
		return modelMapper.map(profession, ProfessionInfoDto.class);
	}

	@Override
	@Transactional
	public ProfessionInfoDto update(ProfessionUpdateDto professionUpdate) {
		/**
		 * Vérification de profession
		 */
		if (!professionRepository.existsById(professionUpdate.getId())) {
			throw new ResourceNotFoundException("Profession non trouvée !");
		}
		
		Profession professionOld = professionRepository.getOne(professionUpdate.getId());

		if (!professionOld.getName().equals(professionUpdate.getName())
				&& professionRepository.existsByName(professionUpdate.getName()))
			throw new ResourceAlreadyExistException("Profession existe déja !");

		/**
		 * Modifier
		 */
		Profession profession = modelMapper.map(professionUpdate, Profession.class);
		professionRepository.save(profession);

		/**
		 * Retourne data
		 */
		return modelMapper.map(profession, ProfessionInfoDto.class);
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {

		/**
		 * Vérification de profession
		 */
		Profession profession = professionRepository.getOne(id);
		if (profession == null) {
			throw new ResourceNotFoundException("Profession non trouvée !");
		}

		if (contactRepository.existsByProfession(profession))
			throw new ResourceAlreadyExistException("Profession déja utilisée !");

		try {
			/**
			 * Supprimer
			 */
			professionRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ProfessionInfoDto getOne(Integer id) {
		/**
		 * Vérification de profession
		 */
		if (!professionRepository.existsById(id)) {
			throw new ResourceNotFoundException("Profession non trouvée !");
		}
		
		Profession profession = professionRepository.getOne(id);

		return modelMapper.map(profession, ProfessionInfoDto.class);
	}

	@Override
	public List<ProfessionInfoDto> getList() {
		return Utils.map(modelMapper, professionRepository.findAll(), ProfessionInfoDto.class);
	}

}

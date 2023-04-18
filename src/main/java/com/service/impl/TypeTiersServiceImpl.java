package com.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.TypeTiersAddDto;
import com.dto.TypeTiersInfoDto;
import com.dto.TypeTiersUpdateDto;
import com.entity.Tiers;
import com.entity.TypeTiers;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceNotFoundException;
import com.repository.TiersRepository;
import com.repository.TypeTiersRepository;
import com.service.TypeTiersService;
import com.util.Utils;

@Service
public class TypeTiersServiceImpl implements TypeTiersService {

	@Autowired
	private TypeTiersRepository typeTiersRepository;

	@Autowired
	private TiersRepository<Tiers> tiersRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public TypeTiersInfoDto add(TypeTiersAddDto typeTiersAdd) {
		/**
		 * Vérification de type du tiers
		 */
		if (typeTiersRepository.existsByName(typeTiersAdd.getName()))
			throw new ResourceAlreadyExistException("Type de tiers existe déja !");

		/**
		 * Ajouter
		 */
		TypeTiers typeTiers = modelMapper.map(typeTiersAdd, TypeTiers.class);
		typeTiersRepository.save(typeTiers);

		/**
		 * Retourne data
		 */
		return modelMapper.map(typeTiers, TypeTiersInfoDto.class);
	}

	@Override
	@Transactional
	public TypeTiersInfoDto update(TypeTiersUpdateDto typeTiersUpdate) {
		/**
		 * Vérification de type de tiers
		 */
		if (!typeTiersRepository.existsById(typeTiersUpdate.getId())) {
			throw new ResourceNotFoundException("Type de tiers non trouvé !");
		}
		
		TypeTiers typeTierOld = typeTiersRepository.getOne(typeTiersUpdate.getId());

		if (!typeTierOld.getName().equals(typeTiersUpdate.getName())
				&& typeTiersRepository.existsByName(typeTiersUpdate.getName()))
			throw new ResourceAlreadyExistException("Type de tiers existe déja !");

		/**
		 * Modifier
		 */
		TypeTiers typeTier = modelMapper.map(typeTiersUpdate, TypeTiers.class);
		typeTiersRepository.save(typeTier);

		/**
		 * Retourne data
		 */
		return modelMapper.map(typeTier, TypeTiersInfoDto.class);
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {
		/**
		 * Vérification de type de tiers
		 */
		if (!typeTiersRepository.existsById(id)) {
			throw new ResourceNotFoundException("Type de tiers non trouvé !");
		}

		TypeTiers typeTiers = typeTiersRepository.getOne(id);
		
		if (tiersRepository.existsByTypeTiers(typeTiers))
			throw new ResourceAlreadyExistException("Type de tiers déja utilisé !");

		try {
			/**
			 * Supprimer
			 */
			typeTiersRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public TypeTiersInfoDto getOne(Integer id) {
		/**
		 * Vérification de type du tiers
		 */
		if (!typeTiersRepository.existsById(id)) {
			throw new ResourceNotFoundException("Type de tiers non trouvé !");
		}
		
		TypeTiers typeTiers = typeTiersRepository.getOne(id);

		return modelMapper.map(typeTiers, TypeTiersInfoDto.class);
	}

	@Override
	public List<TypeTiersInfoDto> getList() {
		return Utils.map(modelMapper, typeTiersRepository.findAll(), TypeTiersInfoDto.class);
	}

}
